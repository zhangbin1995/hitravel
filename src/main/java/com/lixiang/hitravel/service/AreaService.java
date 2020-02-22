package com.lixiang.hitravel.service;

import com.lixiang.hitravel.result.Result;

/**
 * @author binzhang
 * @date 2020-01-28
 */
public interface AreaService {

    // 查询全部省份
    Result findAllProvince(Integer pageNo, Integer pageSize);

    // 根据省份Code查询所有市
    Result findAllCityByPCode(String provinceCode, Integer pageNo, Integer pageSize);

    // 根据市Code查询所有区
    Result findAllAreaByCCode(String cityCode, Integer pageNo, Integer pageSize);

    // 根据区Code查询所有街道
    Result findAllStreetByACode(String areaCode, Integer pageNo, Integer pageSize);

}
