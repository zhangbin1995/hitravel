package com.lixiang.hitravel.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author binzhang
 * @date 2020-02-10
 */
@Data
public class OrderDto {
    @ApiModelProperty(value = "订单里的订单项列表",example="订单里的订单项列表")
    private List<OrderDetailDto> orderdetailList;
}
