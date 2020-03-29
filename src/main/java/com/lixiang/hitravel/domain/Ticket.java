package com.lixiang.hitravel.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author binzhang
 * @date 2020-02-10
 */
@Data
@TableName(value = "t_ticket")
public class Ticket {
    // 门票表主键id
    @TableId(type = IdType.AUTO)
    private Integer ticketId;

    // 所属景区
    private Integer scenicId;

    // 所属景区名称
    private String scenicName;

    // 门票名称
    private String ticketName;

    // 是否在售 0-不在售 1-在售
    private Integer status;

    // 门票原价
    private BigDecimal normalPrice;

    // 门票售价(折扣价)
    private BigDecimal sellPrice;
}
