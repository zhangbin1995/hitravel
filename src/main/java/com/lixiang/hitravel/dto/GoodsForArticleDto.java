package com.lixiang.hitravel.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author zhang
 * @date 2020-04-29
 */
@Data
public class GoodsForArticleDto {

    @ApiModelProperty(value = "文章id",example="1,文章id")
    private Integer articleId;

    @ApiModelProperty(value = "商品列表",example="商品列表，每条记录包含两个字段，商品id，商品类型")
    private List<GoodsDto> goodsList;
}
