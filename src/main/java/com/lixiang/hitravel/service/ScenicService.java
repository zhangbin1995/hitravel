package com.lixiang.hitravel.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lixiang.hitravel.domain.Hotel;
import com.lixiang.hitravel.domain.Scenic;
import com.lixiang.hitravel.result.Result;

/**
 * @author binzhang
 * @date 2020-01-21
 */
public interface ScenicService {

    // 添加景区
    Result save(String token, Scenic scenic);

    // 删除景区
    Result delete(Integer scenicId);

    // 审核景区
    Result passVerify(Integer scenicId);

    // 修改景区信息
    Result update(Scenic scenic);

    // 查看景区信息
    Result queryById(Integer scenicId);

    // 分页查询景区
    Result queryByPage(String cityCode, Integer status, Integer pageNo, Integer pageSize);

    // 查询我的景区
    Result queryMyScenic(String token);
}
