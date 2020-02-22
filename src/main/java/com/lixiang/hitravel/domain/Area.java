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
@TableName(value = "bs_area")
public class Area {

    // 区表主键id
    @TableId(type = IdType.AUTO)
    private Integer areaId;

    // 区代码
    private String areaCode;

    // 区名称
    private String areaName;

    // 简称
    private String shortName;

    // 市代码
    private String cityCode;

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
