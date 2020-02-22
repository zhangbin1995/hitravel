package com.lixiang.hitravel.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author binzhang
 * @date 2020-01-28
 */
@Data
@TableName(value = "bs_city")
public class City {

    // 市表主键id
    @TableId(type = IdType.AUTO)
    private Integer cityId;

    // 市代码
    private String cityCode;

    // 市名称
    private String cityName;

    // 简称
    private String shortName;

    // 省代码
    private String provinceCode;

    // 经度
    private String lng;

    // 纬度
    private String lat;

    // 排序
    private Integer sort;

    // 创建时间
    private Date gmtCreate;

    // 修改时间
    private Date gmtModified;

    // 备注
    private String memo;

    // 状态
    private Integer dataState;

    // 租户id
    private String tenantCode;
}
