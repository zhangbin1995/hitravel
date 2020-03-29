package com.lixiang.hitravel.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author binzhang
 * @date 2020-01-20
 */
@Data
@TableName(value = "t_room")
public class Room {

    // 房间表主键id
    @TableId(type = IdType.AUTO)
    private Integer roomId;

    // 所属酒店
    private Integer hotelId;

    // 所属酒店名称
    private String hotelName;

    // 房间类型
    private String roomName;

    // 房间数量
    private Integer roomNumber;

    // 是否在售 0-不在售 1-在售
    private Integer status;

    // 房间原价
    private BigDecimal normalPrice;

    // 房间售价(折扣价)
    private BigDecimal sellPrice;
}
