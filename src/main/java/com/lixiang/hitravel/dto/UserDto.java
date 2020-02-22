package com.lixiang.hitravel.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author binzhang
 * @date 2020-01-10
 */
@Data
public class UserDto {

    @ApiModelProperty(value = "主键Id，新增不传，修改传",example="主键Id，新增不传，修改传")
    private Integer userId;

    @ApiModelProperty(value = "用户名",example="用户名")
    private String username;

    @ApiModelProperty(value = "密码",example="密码")
    private String password;

    @ApiModelProperty(value = "真实姓名",example="真实姓名")
    private String realname;

    @ApiModelProperty(value = "身份证号",example="身份证号")
    private String cardId;

    @ApiModelProperty(value = "邮箱",example="邮箱")
    private String email;

    @ApiModelProperty(value = "手机号码",example="手机号码")
    private String phone;

    @ApiModelProperty(value = "创建时间",example="创建时间,不传后台生成")
    private Date createTime;

    @ApiModelProperty(value = "用户类型 0-管理员 1-普通用户 2-酒店 3-景区",example="用户类型 0-管理员 1-普通用户 2-酒店 3-景区")
    private Integer userType;

    @ApiModelProperty(value = "用户状态 0-未激活 1-已激活 2-已删除 普通用户默认1 商家默认0",example="用户状态 0-未激活 1-已激活 2-已删除 普通用户默认1 商家默认0")
    private Integer userStatus;
}
