package com.lixiang.hitravel.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author binzhang
 * @date 2020-02-10
 */
@Data
public class OrderDetailDto {
    @ApiModelProperty(value = "订单详情表主键id",example="订单详情表主键id")
    private Integer orderDetailId;

    @ApiModelProperty(value = "所属订单",example="所属订单")
    private Integer orderId;

    @ApiModelProperty(value = "订单类型 0 酒店房间 1 景区门票",example="订单类型 0 酒店房间 1 景区门票")
    private Integer orderType;

    @ApiModelProperty(value = "房间/门票 id",example="房间/景区 id")
    private Integer hotelOrScenicId;

    @ApiModelProperty(value = "房间/门票 名称",example="房间/景区 名称")
    private String hotelOrScenicName;

    @ApiModelProperty(value = "订单金额",example="订单金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "预订时间",example="预订时间")
    private Date bookTime;
}
