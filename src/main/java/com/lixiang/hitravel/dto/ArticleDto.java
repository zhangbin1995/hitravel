package com.lixiang.hitravel.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author zhang
 * @date 2020-02-17
 */
@Data
public class ArticleDto {

    @ApiModelProperty(value = "主键Id，新增不传，修改传",example="主键Id，新增不传，修改传")
    private Integer articleId;

    @ApiModelProperty(value = "标题",example="标题")
    private String title;

    @ApiModelProperty(value = "正文内容",example="正文内容")
    private String content;

    @ApiModelProperty(value = "城市编码",example="城市编码")
    private String cityCode;

    @ApiModelProperty(value = "城市名称",example="城市名称")
    private String cityName;

    @ApiModelProperty(value = "状态 0-未审核 1-审核",example="状态 0-未审核 1-审核 新建不传 查询可传")
    private Integer status;
}
