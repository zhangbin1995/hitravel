package com.lixiang.hitravel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lixiang.hitravel.domain.Scenic;
import com.lixiang.hitravel.domain.Ticket;
import com.lixiang.hitravel.exception.GlobalException;
import com.lixiang.hitravel.mapper.ScenicMapper;
import com.lixiang.hitravel.mapper.TicketMapper;
import com.lixiang.hitravel.result.CodeMsg;
import com.lixiang.hitravel.result.Result;
import com.lixiang.hitravel.service.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author binzhang
 * @date 2020-02-10
 */
@Service
public class TicketServiceImpl implements TicketService {

    private final static Logger log = LoggerFactory.getLogger(TicketServiceImpl.class);

    @Autowired
    private TicketMapper ticketMapper;

    @Autowired
    private ScenicMapper scenicMapper;

    @Override
    public Result save(Ticket ticket) {
        try {
            Scenic scenic = scenicMapper.selectById(ticket.getScenicId());
            if (scenic == null) {
                throw new GlobalException("所属景区信息错误");
            }
            ticket.setScenicName(scenic.getScenicName());
            int res = ticketMapper.insert(ticket);
            if (res > 0) {
                return Result.success("新增门票成功");
            } else {
                return Result.success("新增门票失败");
            }
        } catch (GlobalException e) {
            log.info("新增门票错误：" + e.getMessage());
            return Result.error(CodeMsg.SERVER_ERROR, "新增门票错误：" + e.getMessage());
        }
    }

    @Override
    public Result delete(Integer ticketId) {
        return null;
    }

    @Override
    public Result updateTicketInfo(Ticket ticket) {
        try {
            int res = ticketMapper.updateById(ticket);
            if (res > 0) {
                return Result.success("修改门票信息成功");
            } else {
                return Result.success("修改门票信息失败");
            }
        } catch (GlobalException e) {
            return Result.error(CodeMsg.SERVER_ERROR, "修改门票信息错误：" + e.getMessage());
        }
    }

    @Override
    public Result findTicketByPage(IPage<Ticket> page, QueryWrapper<Ticket> wrapper) {
        return null;
    }

    @Override
    public Result queryById(Integer ticketId) {
        return null;
    }

    @Override
    public Result queryByScenicId(Integer scenicId, Integer pageNo, Integer pageSize) {
        try {
            QueryWrapper<Ticket> wrapper = new QueryWrapper<>();
            IPage<Ticket> page = new Page<>(pageNo, pageSize);
            Ticket temp = new Ticket();
            temp.setScenicId(scenicId);
            wrapper.setEntity(temp);
            return Result.success(ticketMapper.selectPage(page, wrapper));
        } catch (GlobalException e) {
            return Result.error(CodeMsg.SERVER_ERROR, "查询门票信息错误：" + e.getMessage());
        }
    }
}
