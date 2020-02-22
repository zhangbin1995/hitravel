package com.lixiang.hitravel.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author binzhang
 * @date 2020-02-10
 */
@Data
@TableName(value = "t_order_detail")
public class OrderDetail {
    // 订单详情表主键id
    @TableId(type = IdType.AUTO)
    private Integer orderDetailId;
    // 订单类型 0 酒店 1 景区
    private Integer orderType;
    // 酒店/景区 id
    private Integer hotelOrScenicId;
    // 酒店/景区 名称
    private String hotelOrScenicName;
    // 房间或票 id
    private Integer roomOrTicketId;
    // 房间或票名称
    private String roomOrTicketName;
    // 数量
    private Integer number;
    // 订单金额
    private BigDecimal amount;
    // 预订时间
    private String bookTime;
    // 创建时间
    private Date createTime;
}
