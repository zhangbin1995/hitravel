package com.lixiang.hitravel.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zhang
 * @date 2020-02-10
 */
@Data
@TableName(value = "t_order_detail")
public class OrderDetail {
    // 订单详情表主键id
    @TableId(type = IdType.AUTO)
    private Integer orderDetailId;
    // 所属订单
    private Integer orderId;
    // 订单所属用户
    private Integer userId;
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
    // 订单状态 0 已预订 1 已取消
    private Integer status;
}
