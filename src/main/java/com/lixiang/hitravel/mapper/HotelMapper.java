package com.lixiang.hitravel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lixiang.hitravel.domain.Hotel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author zhang
 * @date 2020-01-21
 */
public interface HotelMapper extends BaseMapper<Hotel> {

    @Select("select * from t_hotel h where h.hotel_id in (select r.hotel_id from t_user_hotel_ref r where r.user_id = #{userId})")
    List<Hotel> queryMyHotel(@Param("userId") Integer userId);
}
