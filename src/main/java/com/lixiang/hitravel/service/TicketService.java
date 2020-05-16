package com.lixiang.hitravel.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lixiang.hitravel.domain.Ticket;
import com.lixiang.hitravel.result.Result;

/**
 * @author zhang
 * @date 2020-01-22
 */
public interface TicketService {
    // 添加门票
    Result save(Ticket ticket);

    // 删除房间
    Result delete(Integer ticketId);

    // 修改房间信息
    Result updateTicketInfo(Ticket ticket);

    // 分页查询房间
    Result findTicketByPage(IPage<Ticket> page, QueryWrapper<Ticket> wrapper);

    Result queryById(Integer ticketId);

    // 分页查询某景区下的门票
    Result queryByScenicId(Integer scenicId, Integer pageNo, Integer pageSize);
}
