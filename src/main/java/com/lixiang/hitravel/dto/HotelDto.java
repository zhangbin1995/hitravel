package com.lixiang.hitravel.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author binzhang
 * @date 2020-01-20
 */
@Data
public class HotelDto {

    @ApiModelProperty(value = "主键Id，新增不传，修改传",example="主键Id，新增不传，修改传")
    private Integer hotelId;

    @ApiModelProperty(value = "酒店名",example="酒店名")
    private String hotelName;

    @ApiModelProperty(value = "营业执照号码",example="营业执照号码")
    private String hotelCardId;

    @ApiModelProperty(value = "所属省份编码",example="所属省份编码")
    private String provinceCode;

    @ApiModelProperty(value = "所属省份名称",example="所属省份名称")
    private String provinceName;

    @ApiModelProperty(value = "所属市编码",example="所属市编码")
    private String cityCode;

    @ApiModelProperty(value = "所属市名称",example="所属市名称")
    private String cityName;

    @ApiModelProperty(value = "所属区编码",example="所属区编码")
    private String areaCode;

    @ApiModelProperty(value = "所属区名称",example="所属区名称")
    private String areaName;

    @ApiModelProperty(value = "所属街道编码",example="所属街道编码")
    private String streetCode;

    @ApiModelProperty(value = "所属街道名称",example="所属街道名称")
    private String streetName;

    @ApiModelProperty(value = "法人姓名",example="法人姓名")
    private String realname;

    @ApiModelProperty(value = "法人身份证号",example="法人身份证号")
    private String cardId;

    @ApiModelProperty(value = "邮箱",example="邮箱")
    private String email;

    @ApiModelProperty(value = "手机号",example="手机号")
    private String phone;

    @ApiModelProperty(value = "创建时间",example="创建时间")
    private Date createTime;

    @ApiModelProperty(value = "审核状态 0-未激活 1-已激活 2-已删除",example="审核状态 0-未激活 1-已激活 2-已删除")
    private Integer status;

    @ApiModelProperty(value = "图片地址",example="图片地址")
    private String img;
}
