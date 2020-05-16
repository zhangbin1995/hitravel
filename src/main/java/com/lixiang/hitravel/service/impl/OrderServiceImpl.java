package com.lixiang.hitravel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lixiang.hitravel.domain.*;
import com.lixiang.hitravel.exception.GlobalException;
import com.lixiang.hitravel.mapper.OrderDetailMapper;
import com.lixiang.hitravel.mapper.OrderMapper;
import com.lixiang.hitravel.mapper.RoomMapper;
import com.lixiang.hitravel.mapper.TicketMapper;
import com.lixiang.hitravel.redis.RedisService;
import com.lixiang.hitravel.redis.UserKey;
import com.lixiang.hitravel.result.CodeMsg;
import com.lixiang.hitravel.result.Result;
import com.lixiang.hitravel.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * @author zhang
 * @date 2020-03-07
 */
@Service
@SuppressWarnings("all")
public class OrderServiceImpl implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private TicketMapper ticketMapper;

    @Autowired
    private RedisService redisService;

    @Override
    @Transactional
    public Result addOrder(String token, List<OrderDetail> list) {
        try {
            // 1. 新建一个总的订单记录
            Order order = new Order();
            User user = redisService.get(UserKey.token, token, User.class);
            if (user == null) {
                return Result.error(CodeMsg.ERROR, "当前用户为空");
            }
            order.setUserId(user.getUserId());
            // 订单初始状态为未支付
            order.setStatus(0);
            order.setCreateTime(new Date());
            order.setAmount(new BigDecimal(0));
            int res = orderMapper.insert(order);
            if (res < 0) {
                throw new GlobalException("订单插入失败");
            }
            int orderId = order.getOrderId();
            BigDecimal amount = new BigDecimal(0.0);
            // 2. 拆解每一个订单详情记录， 分别保存
            for (int i = 0 ; i < list.size() ; i ++) {
                OrderDetail detail = list.get(i);
                // 已知的有 房间/票id 类型 数量 预订时间
                // 根据类型判断预订的是票或酒店 0酒店 1景区
                if (detail.getOrderType() == 0) {
                    // 查询对应房间信息
                    Room room = roomMapper.selectById(detail.getRoomOrTicketId());
                    if (room == null) {
                        throw new GlobalException("查询房间信息错误!");
                    }
                    // 验证房间余量是否足够
                    QueryWrapper<OrderDetail> wrapper = new QueryWrapper<>();
                    OrderDetail temp = new OrderDetail();
                    temp.setRoomOrTicketId(detail.getRoomOrTicketId());
                    temp.setOrderType(detail.getOrderType());
                    temp.setBookTime(detail.getBookTime());
                    wrapper.setEntity(temp);
                    List<OrderDetail> tempList = orderDetailMapper.selectList(wrapper);
                    int bookCount = 0;
                    if (tempList != null && tempList.size() > 0) {
                        for (OrderDetail d : tempList) {
                            bookCount += d.getNumber();
                        }
                        if (bookCount + detail.getNumber() > room.getRoomNumber()) {
                            throw new GlobalException("订购失败，房间数量不足");
                        }
                    }

                    // 添加金额
                    detail.setAmount(room.getSellPrice().multiply(new BigDecimal(detail.getNumber())));
                    // 添加酒店id
                    detail.setHotelOrScenicId(room.getHotelId());
                    // 添加酒店名称
                    detail.setHotelOrScenicName(room.getHotelName());
                    // 添加房间名称
                    detail.setRoomOrTicketName(room.getRoomName());

                } else {
                    // 查询对应票的信息
                    Ticket ticket = ticketMapper.selectById(detail.getRoomOrTicketId());
                    if (ticket == null) {
                        throw new GlobalException("查询门票信息错误!");
                    }
                    // 添加金额
                    detail.setAmount(ticket.getSellPrice().multiply(new BigDecimal(detail.getNumber())));
                    // 添加景区id
                    detail.setHotelOrScenicId(ticket.getScenicId());
                    // 添加景区名称
                    detail.setHotelOrScenicName(ticket.getScenicName());
                    // 添加门票名称
                    detail.setRoomOrTicketName(ticket.getTicketName());
                }
                // 添加时间
                detail.setCreateTime(new Date());
                // 添加所属订单
                detail.setOrderId(orderId);
                // 添加所属用户
                detail.setUserId(order.getUserId());
                // 设置订单初始状态为 0-未支付
                detail.setStatus(0);
                // 将金额累加
                amount = amount.add(detail.getAmount());
                int detailRes = orderDetailMapper.insert(detail);
                if (detailRes <= 0) {
                    throw new GlobalException("插入订单单条记录失败");
                }
            }
            order.setAmount(amount);
            int orderRes = orderMapper.updateById(order);
            if (orderRes > 0) {
                return Result.success(order);
            } else {
                throw new GlobalException("订单插入失败");
            }
        } catch (GlobalException e) {
            throw new GlobalException("订单新建失败：" + e.getMessage());
        }

    }

    @Override
    @Transactional
    public Result cancelOrder(Integer orderId) {
        try {
            QueryWrapper<OrderDetail> wrapper = new QueryWrapper<>();
            OrderDetail temp = new OrderDetail();
            temp.setOrderId(orderId);
            wrapper.setEntity(temp);
            List<OrderDetail> list = orderDetailMapper.selectList(wrapper);
            if (list == null || list.size() <= 0) {
                return Result.error(CodeMsg.ERROR, "订单下无订单项");
            }
            for (OrderDetail detail : list) {
                // 设置状态为取消
                detail.setStatus(1);
                int res = orderDetailMapper.updateById(detail);
                if (res <= 0) {
                    throw new GlobalException("修改订单状态失败");
                }
            }
            Order order = orderMapper.selectById(orderId);
            // 设置订单状态为取消
            order.setStatus(1);
            int res = orderMapper.updateById(order);
            if (res <= 0) {
                throw new GlobalException("修改订单状态失败");
            }
            return Result.success(order);
        } catch (GlobalException e) {
            log.info("取消订单失败：" + e.getMessage());
            throw new GlobalException("订单新建失败：" + e.getMessage());
        }
    }

    @Override
    public Result myOrder(String token, Integer status, Integer orderType, Integer pageNo, Integer pageSize) {
        try {
            User user = redisService.get(UserKey.token, token, User.class);
            if (user == null) {
                return Result.error(CodeMsg.ERROR, "当前用户为空");
            }
            QueryWrapper<OrderDetail> wrapper = new QueryWrapper<>();
            wrapper.orderByDesc("create_time");
            IPage<OrderDetail> page = new Page<>(pageNo, pageSize);
            OrderDetail temp = new OrderDetail();
            temp.setUserId(user.getUserId());
            if (status != null) {
                temp.setStatus(status);
            }
            if (orderType != null) {
                temp.setOrderType(orderType);
            }
            wrapper.setEntity(temp);
            return Result.success(orderDetailMapper.selectPage(page, wrapper));
        } catch (GlobalException e) {
            log.info("查询订单失败：" + e.getMessage());
            throw new GlobalException("查询订单失败：" + e.getMessage());
        }
    }

    @Override
    public Result myOrderForStore(Integer hotelOrScenicId, Integer status, Integer orderType, Integer pageNo, Integer pageSize) {
        try {
            QueryWrapper<OrderDetail> wrapper = new QueryWrapper<>();
            wrapper.orderByDesc("create_time");
            IPage<OrderDetail> page = new Page<>(pageNo, pageSize);
            OrderDetail temp = new OrderDetail();
            temp.setHotelOrScenicId(hotelOrScenicId);
            if (status != null) {
                temp.setStatus(status);
            }
            if (orderType != null) {
                temp.setOrderType(orderType);
            }
            wrapper.setEntity(temp);
            return Result.success(orderDetailMapper.selectPage(page, wrapper));
        } catch (GlobalException e) {
            log.info("查询订单失败：" + e.getMessage());
            throw new GlobalException("查询订单失败：" + e.getMessage());
        }
    }
}
