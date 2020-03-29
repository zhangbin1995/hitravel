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
@TableName(value = "t_order")
public class Order {
    // 订单表主键id
    @TableId(type = IdType.AUTO)
    private Integer orderId;
    // 订单所属用户
    private Integer userId;
    // 订单总金额
    private BigDecimal amount;
    // 创建日期
    private Date createTime;
    // 订单状态 0 已预订 1 已取消
    private Integer status;
}
