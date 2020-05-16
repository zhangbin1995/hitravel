package com.lixiang.hitravel.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author zhang
 * @date 2020-01-20
 */
@Data
public class ScenicDto {

    @ApiModelProperty(value = "主键Id，新增不传，修改传",example="主键Id，新增不传，修改传")
    private Integer scenicId;

    @ApiModelProperty(value = "景区名",example="景区名")
    private String scenicName;

    @ApiModelProperty(value = "所属用户",example="所属用户，不传，token中可获取")
    private Integer userId;

    @ApiModelProperty(value = "营业执照号码",example="营业执照号码")
    private String scenicCardId;

    @ApiModelProperty(value = "所属市编码",example="所属市编码")
    private String cityCode;

    @ApiModelProperty(value = "所属市名称",example="所属市名称")
    private String cityName;

    @ApiModelProperty(value = "所属省编码",example="所属省编码")
    private String provinceCode;

    @ApiModelProperty(value = "所属省名称",example="所属省名称")
    private String provinceName;

    @ApiModelProperty(value = "法人姓名",example="法人姓名")
    private String realname;

    @ApiModelProperty(value = "法人身份证号",example="法人身份证号")
    private String cardId;

    @ApiModelProperty(value = "邮箱",example="邮箱")
    private String email;

    @ApiModelProperty(value = "手机号",example="手机号")
    private String phone;

    @ApiModelProperty(value = "创建时间",example="创建时间,不传,后台生成")
    private Date createTime;

    @ApiModelProperty(value = "审核状态 0-未激活 1-已激活 2-已删除",example="审核状态 0-未激活 1-已激活 2-已删除,新建不传，默认未审核")
    private Integer status;

    @ApiModelProperty(value = "图片地址",example="图片地址")
    private String img;
}
