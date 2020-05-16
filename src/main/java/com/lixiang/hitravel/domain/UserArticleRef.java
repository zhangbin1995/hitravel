package com.lixiang.hitravel.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 用户攻略关联表
 * @author zhang
 * @date 2020-02-18
 */
@Data
@TableName(value = "t_user_article_ref")
public class UserArticleRef {
    // 用户攻略关联表主键id
    @TableId(type = IdType.AUTO)
    private Integer userArticleRefId;
    // 用户Id
    private Integer userId;
    // 攻略Id
    private Integer articleId;
}
