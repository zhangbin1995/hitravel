package com.lixiang.hitravel.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author binzhang
 * @date 2020-02-17
 */
@Data
@TableName(value = "t_article")
public class Article {

    @TableId(type = IdType.AUTO)
    private Integer articleId;

    // 标题
    private String title;

    // 正文内容
    private String content;

    // 城市编码
    private String cityCode;

    // 城市名称
    private String cityName;

    // 收藏数
    private Integer collection;

    // 点赞数
    private Integer praise;

    // 浏览量
    private Integer view;

    // 发帖人Id
    private Integer userId;

    // 发帖人姓名
    private String username;

    // 创建时间
    private Date createTime;

    // 状态 0-未审核 1-已审核
    private Integer status;
}
