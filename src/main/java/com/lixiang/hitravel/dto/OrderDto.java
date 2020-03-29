package com.lixiang.hitravel.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author binzhang
 * @date 2020-02-10
 */
@Data
public class OrderDto {

    @ApiModelProperty(value = "要购买的商品id集合",example="[1,3,1,2]要购买的商品id集合")
    private int[] ids;

    @ApiModelProperty(value = "商品对应的类型集合 0酒店 1景区",example="[0,1,1,0]商品对应的类型集合 0酒店 1景区")
    private int[] types;

    @ApiModelProperty(value = "要预订的日期集合",example="['2020-01-01','2020-01-02','2020-01-03','2020-01-04']要预订的日期集合")
    private String[] dates;

    @ApiModelProperty(value = "对应该日期的数量",example="[1,1,2,2]对应该日期的数量")
    private int[] nums;

}
