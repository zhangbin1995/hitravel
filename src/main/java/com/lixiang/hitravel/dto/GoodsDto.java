package com.lixiang.hitravel.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhang
 * @date 2020-04-29
 */
@Data
public class GoodsDto {

    @ApiModelProperty(value = "要购买的商品id",example="1,要购买的商品id")
    private int id;

    @ApiModelProperty(value = "商品对应的类型 0酒店 1景区",example="0/1商品对应的类型 0酒店 1景区")
    private int type;
}
