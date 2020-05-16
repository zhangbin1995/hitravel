package com.lixiang.hitravel.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author zhang
 * @date 2020-01-10
 */
@Data
@TableName(value = "t_user")
public class User {
    // 用户表主键id
    @TableId(type = IdType.AUTO)
    private Integer userId;
    // 用户名
    private String username;
    // 密码
    private String password;
    // 姓名
    private String realname;
    // 身份证号
    private String cardId;
    // 邮箱
    private String email;
    // 手机号
    private String phone;
    // 创建时间
    private Date createTime;
    // 用户类型 0-管理员 1-普通用户 2-商家 3-景区
    private Integer userType;
    // 用户状态 0-未激活 1-已激活 2-已删除
    private Integer userStatus;
}
