package com.lixiang.hitravel.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lixiang.hitravel.domain.Hotel;
import com.lixiang.hitravel.result.Result;

/**
 * @author zhang
 * @date 2020-01-21
 */
public interface HotelService {

    // 添加酒店
    Result save(String token, Hotel hotel);

    // 删除酒店
    Result delete(Integer hotelId);

    // 审核酒店（商家）
    Result passVerify(Integer hotelId);

    // 修改酒店信息
    Result updateHotelInfo(Hotel hotel);

    // 分页查询酒店
    Result findHotelByPage(IPage<Hotel> page, QueryWrapper<Hotel> wrapper);

    // 查看酒店信息
    Result queryById(Integer hotelId);

    // 查询某街道下所有酒店(管理员)
    Result queryByPageForAdmin(String streetCode, Integer status, Integer pageNo, Integer pageSize);

    // 查询我的酒店（商家酒店）
    Result queryMyHotel(String token);

    // 根据城市和酒店名分页查询酒店
    Result queryByPage(String cityCode, String hotelName, Integer pageNo, Integer pageSize);
}
