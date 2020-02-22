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
@TableName(value = "t_comment")
public class Comment {

    @TableId(type = IdType.AUTO)
    private Integer commentId;

    // 所属文章
    private Integer articleId;

    // 评论人Id
    private Integer userId;

    // 评论人昵称
    private String username;

    // 评论内容
    private String content;

    // 创建时间
    private Date createTime;
}
