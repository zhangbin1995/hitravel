package com.lixiang.hitravel.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lixiang.hitravel.domain.Room;
import com.lixiang.hitravel.result.Result;

/**
 * @author zhang
 * @date 2020-01-22
 */
public interface RoomService {
    // 添加房间
    Result save(Room room);

    // 删除房间
    Result delete(Integer roomId);

    // 修改房间信息
    Result updateRoomInfo(Room room);

    // 分页查询房间
    Result findRoomByPage(IPage<Room> page, QueryWrapper<Room> wrapper);

    Result queryById(Integer roomId);

    Result queryByHotelId(Integer hotelId, Integer pageNo, Integer pageSize);

    // 查询某酒店下某天的所有房间信息 包含余量
    Result queryByHotelIdAndTime(Integer hotelId, String inDate, Integer pageNo, Integer pageSize);
}
