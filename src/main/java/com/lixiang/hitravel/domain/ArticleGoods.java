package com.lixiang.hitravel.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zhang
 * @date 2020-04-29
 */
@Data
@TableName(value = "t_article_goods")
public class ArticleGoods {

    @TableId(type = IdType.AUTO)
    private Integer articleGoodsId;

    // 攻略id
    private Integer articleId;

    // 商品（酒店/门票）id
    private Integer goodsId;

    // 商品对应的类型 0酒店 1景区
    private Integer type;

    // 酒店或景区名称
    private String hotelOrScenicName;

    // 房间或门票名称
    private String roomOrTicketName;

    // 正常售价
    private BigDecimal normalPrice;

    // 折扣价
    private BigDecimal sellPrice;
}
