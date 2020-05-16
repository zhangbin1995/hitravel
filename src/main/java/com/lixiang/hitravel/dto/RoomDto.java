package com.lixiang.hitravel.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author zhang
 * @date 2020-01-20
 */
@Data
public class RoomDto {

    @ApiModelProperty(value = "主键Id，新增不传，修改传",example="主键Id，新增不传，修改传")
    private Integer roomId;

    @ApiModelProperty(value = "所属酒店",example="所属酒店")
    private Integer hotelId;

    @ApiModelProperty(value = "房间类型",example="房间类型,例:大床房")
    private String roomName;

    @ApiModelProperty(value = "房间数量",example="房间数量")
    private Integer roomNumber;

    @ApiModelProperty(value = "是否在售 0-不在售 1-在售",example="是否在售 0-不在售 1-在售")
    private Integer status;

    @ApiModelProperty(value = "房间原价",example="房间原价")
    private BigDecimal normalPrice;

    @ApiModelProperty(value = "房间售价(折扣价)",example="房间售价(折扣价)")
    private BigDecimal sellPrice;
}
