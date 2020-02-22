package com.lixiang.hitravel.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author binzhang
 * @date 2020-01-20
 */
@Data
@TableName(value = "t_hotel")
public class Hotel {

    // 酒店表主键id
    @TableId(type = IdType.AUTO)
    private Integer hotelId;
    // 酒店名
    private String hotelName;
    // 所属省份编码
    private String provinceCode;
    // 所属省份名称
    private String provinceName;
    // 所属市编码
    private String cityCode;
    // 所属市名称
    private String cityName;
    // 所属区编码
    private String areaCode;
    // 所属区名称
    private String areaName;
    // 所属街道编码
    private String streetCode;
    // 所属街道名称
    private String streetName;
    // 营业执照号
    private String hotelCardId;
    // 法人姓名
    private String realname;
    // 身份证号
    private String cardId;
    // 邮箱
    private String email;
    // 手机号
    private String phone;
    // 创建时间
    private Date createTime;
    // 审核状态 0-未审核/审核不通过 1-已审核 2-已删除
    private Integer status;
    // 资质图片
    private String img;
}
