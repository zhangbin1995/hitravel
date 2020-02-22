package com.lixiang.hitravel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lixiang.hitravel.domain.OrderDetail;
import com.lixiang.hitravel.domain.Room;
import com.lixiang.hitravel.exception.GlobalException;
import com.lixiang.hitravel.mapper.OrderDetailMapper;
import com.lixiang.hitravel.mapper.RoomMapper;
import com.lixiang.hitravel.result.CodeMsg;
import com.lixiang.hitravel.result.Result;
import com.lixiang.hitravel.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author binzhang
 * @date 2020-01-22
 */
@Service
public class RoomServiceImpl implements RoomService {

    private static final Logger log = LoggerFactory.getLogger(RoomServiceImpl.class);

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Override
    @Transactional
    public Result save(Room room) {
        try {
            int res = roomMapper.insert(room);
            if (res > 0) {
                return Result.success("房间注册成功");
            } else {
                return Result.error(CodeMsg.ERROR, "房间注册失败");
            }
        } catch (GlobalException e) {
            log.info("新增房间失败，错误信息：" + e.getMessage());
            return Result.error(CodeMsg.SERVER_ERROR, "保存酒店信息错误，错误信息：" + e.getMessage());
        }
    }

    @Override
    public Result delete(Integer roomId) {
        return null;
    }

    @Override
    public Result updateRoomInfo(Room room) {
        try {
            int res = roomMapper.updateById(room);
            if (res > 0) {
                return Result.success("房间信息修改成功");
            } else {
                return Result.error(CodeMsg.ERROR, "房间信息修改失败");
            }
        } catch (GlobalException e) {
            return Result.error(CodeMsg.SERVER_ERROR, "房间信息修改失败，错误信息：" + e.getMessage());
        }
    }

    @Override
    public Result findRoomByPage(IPage<Room> page, QueryWrapper<Room> wrapper) {
        return null;
    }

    @Override
    public Result queryById(Integer roomId) {
        try {
            Room room = roomMapper.selectById(roomId);
            if (room != null) {
                return Result.success(room);
            } else {
                return Result.error(CodeMsg.ERROR, "房间信息查询失败");
            }
        } catch (GlobalException e) {
            log.info("房间信息查询失败，错误信息：" + e.getMessage());
            return Result.error(CodeMsg.SERVER_ERROR, "房间信息查询失败，错误信息：" + e.getMessage());
        }
    }

    @Override
    public Result queryByHotelId(Integer hotelId, Integer pageNo, Integer pageSize) {
        try {
            QueryWrapper<Room> wrapper = new QueryWrapper<>();
            IPage<Room> page = new Page<>(pageNo, pageSize);
            Room temp = new Room();
            temp.setHotelId(hotelId);
            wrapper.setEntity(temp);
            return Result.success(roomMapper.selectPage(page, wrapper));
        } catch (GlobalException e) {
            log.info("房间信息查询失败，错误信息：" + e.getMessage());
            return Result.error(CodeMsg.SERVER_ERROR, "房间信息查询失败，错误信息：" + e.getMessage());
        }
    }

    @Override
    public Result queryByHotelIdAndTime(Integer hotelId, String inDate, Integer pageNo, Integer pageSize) {
        try {
            // 先查询出该酒店下所有房间
            QueryWrapper<Room> wrapper = new QueryWrapper<>();
            Room temp = new Room();
            temp.setHotelId(hotelId);
            wrapper.setEntity(temp);
            List<Room> roomList = roomMapper.selectList(wrapper);
            List<Room> resList = new ArrayList<>();
            for (Room r : roomList) {
                // 根据房间Id 和 时间查询出改天已经被订的房间
                QueryWrapper<OrderDetail> detailWrapper = new QueryWrapper<>();
                OrderDetail tempDetail = new OrderDetail();
                tempDetail.setBookTime(inDate);
                tempDetail.setRoomOrTicketId(r.getRoomId());
                detailWrapper.setEntity(tempDetail);
                List<OrderDetail> details = orderDetailMapper.selectList(detailWrapper);
                int tempCount = 0;
                for (OrderDetail od : details) {
                    tempCount += od.getNumber();
                }
                r.setRoomNumber(r.getRoomNumber()-tempCount);
                resList.add(r);
            }
            return Result.success(resList);

        } catch (GlobalException e) {
            log.info("房间信息查询失败，错误信息：" + e.getMessage());
            return Result.error(CodeMsg.SERVER_ERROR, "房间信息查询失败，错误信息：" + e.getMessage());
        }
    }
}
