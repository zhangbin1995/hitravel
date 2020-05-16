package com.lixiang.hitravel.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 用户酒店关联表
 * @author zhang
 * @date 2020-01-31
 */
@Data
@TableName(value = "t_user_hotel_ref")
public class UserHotelRef {
    // 用户酒店关联表主键id
    @TableId(type = IdType.AUTO)
    private Integer userHotelRefId;
    // 用户Id
    private Integer userId;
    // 酒店Id
    private Integer hotelId;
    // 状态 0-删除 1-正常
    private Integer status;
}
