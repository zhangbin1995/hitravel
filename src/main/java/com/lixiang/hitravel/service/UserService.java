package com.lixiang.hitravel.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lixiang.hitravel.domain.User;
import com.lixiang.hitravel.result.Result;

/**
 * @author binzhang
 * @date 2020-01-16
 */
public interface UserService {

    // 添加用户
    Result save(User user);

    // 删除用户
    Result delete(Integer userId);

    // 审核用户（商家）
    Result passVerify(Integer userId);

    // 修改用户信息
    Result updateUserInfo(User user);

    // 分页查询用户
    Result findUserByPage(IPage<User> page, QueryWrapper<User> wrapper);

    Result login(String username, String password, Integer type);
}
