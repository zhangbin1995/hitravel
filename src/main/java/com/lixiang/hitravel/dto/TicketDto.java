package com.lixiang.hitravel.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zhang
 * @date 2020-01-20
 */
@Data
public class TicketDto {

    @ApiModelProperty(value = "主键Id，新增不传，修改传",example="主键Id，新增不传，修改传")
    private Integer ticketId;

    @ApiModelProperty(value = "所属景区",example="所属景区")
    private Integer scenicId;

    @ApiModelProperty(value = "门票名称",example="门票名称")
    private String ticketName;

    @ApiModelProperty(value = "是否在售 0-不在售 1-在售",example="是否在售 0-不在售 1-在售")
    private Integer status;

    @ApiModelProperty(value = "门票原价",example="门票原价")
    private BigDecimal normalPrice;

    @ApiModelProperty(value = "门票售价(折扣价)",example="门票售价(折扣价)")
    private BigDecimal sellPrice;
}
