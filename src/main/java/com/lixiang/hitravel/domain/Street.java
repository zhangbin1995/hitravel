package com.lixiang.hitravel.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author zhang
 * @date 2020-01-28
 */
@Data
@TableName(value = "bs_street")
public class Street {

    // 街道表主键id
    @TableId(type = IdType.AUTO)
    private Integer streetId;

    // 街道代码
    private String streetCode;

    // 父级区代码
    private String areaCode;

    // 街道名称
    private String streetName;

    // 简称
    private String shortName;

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
