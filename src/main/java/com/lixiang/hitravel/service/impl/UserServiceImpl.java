package com.lixiang.hitravel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lixiang.hitravel.domain.User;
import com.lixiang.hitravel.enums.UserEnum;
import com.lixiang.hitravel.mapper.UserMapper;
import com.lixiang.hitravel.redis.RedisService;
import com.lixiang.hitravel.redis.UserKey;
import com.lixiang.hitravel.result.CodeMsg;
import com.lixiang.hitravel.result.Result;
import com.lixiang.hitravel.service.UserService;
import com.lixiang.hitravel.util.MD5Util;
import com.lixiang.hitravel.util.UUIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author binzhang
 * @date 2020-01-16
 */
@Service
public class UserServiceImpl implements UserService {

    private final static Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public Result save(User user) {
        try {
            user.setCreateTime(new Date());
            user.setPassword(MD5Util.inputPassToDBPass(user.getPassword()));
            // 如果是普通用户 初始状态就是可用的  商家需要平台审核
            if (user.getUserType() == 1) {
                user.setUserStatus(UserEnum.STATUS_VALID.getCode());
            } else {
                user.setUserStatus(UserEnum.STATUS_INVALID.getCode());
            }
            int res = userMapper.insert(user);
            if (res > 0) {
                return Result.success("注册成功");
            } else {
                return Result.error(CodeMsg.ERROR, "注册失败");
            }
        } catch (Exception e) {
            log.error("注册操作失败：" + e.getMessage());
            return Result.error(CodeMsg.SERVER_ERROR, "注册操作失败：" + e.getMessage());
        }
    }

    @Override
    /**
     * 逻辑删除  将状态置为删除状态
     */
    public Result delete(Integer userId) {
        try {
            User user = userMapper.selectById(userId);
            user.setUserStatus(UserEnum.STATUS_DELETE.getCode());
            int res = userMapper.updateById(user);
            if (res > 0) {
                return Result.success(null);
            } else {
                return Result.error(CodeMsg.ERROR, "删除失败");
            }
        } catch (Exception e) {
            log.error("删除操作失败：" + e.getMessage());
            return Result.error(CodeMsg.SERVER_ERROR, "删除操作失败：" + e.getMessage());
        }
    }

    /**
     * 商家审核通过
     * @param userId
     * @return
     */
    @Override
    public Result passVerify(Integer userId) {
        try {
            User user = userMapper.selectById(userId);
            user.setUserStatus(UserEnum.STATUS_VALID.getCode());
            int res = userMapper.updateById(user);
            if (res > 0) {
                return Result.success("审核通过");
            } else {
                return Result.error(CodeMsg.ERROR, "审核失败");
            }
        } catch (Exception e) {
            log.error("审核操作失败：" + e.getMessage());
            return Result.error(CodeMsg.SERVER_ERROR, "审核操作失败：" + e.getMessage());
        }
    }

    @Override
    public Result updateUserInfo(User user) {

        try {
            int res = userMapper.updateById(user);
            if (res > 0) {
                return Result.success(user);
            } else {
                return Result.error(CodeMsg.ERROR, "修改失败");
            }
        } catch (Exception e) {
            log.error("修改操作失败：" + e.getMessage());
            return Result.error(CodeMsg.SERVER_ERROR, "修改操作失败：" + e.getMessage());
        }
    }

    @Override
    public Result findUserByPage(IPage<User> page, QueryWrapper<User> wrapper) {
        return Result.success(userMapper.selectPage(page, wrapper));
    }

    @Override
    public Result login(String username, String password, Integer type) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        User tempuser = new User();
        tempuser.setUsername(username);
        tempuser.setUserType(type);
        tempuser.setUserStatus(UserEnum.STATUS_VALID.getCode());
        wrapper.setEntity(tempuser);
        User user = userMapper.selectOne(wrapper);
        if (user == null) {
            return Result.error(CodeMsg.ERROR, "用户不存在或未通过审核");
        }
        if (user.getPassword().equals(MD5Util.inputPassToDBPass(password))) {
            // 校验通过
            log.info("用户登录成功，用户名：" + username);
            // token为key，用户信息为value 过期时间为10分钟
            String token = UUIDUtil.uuid();
            redisService.set(UserKey.token, token, user);
            Map<String, String> map = new HashMap<>();
            map.put("username", user.getUsername());
            map.put("type", user.getUserType().toString());
            map.put("token", token);
            return Result.success(map);
        } else {
            // 校验不通过
            log.info("用户登录失败，密码错误，用户名：" + username);
            return Result.error(CodeMsg.ERROR, "密码错误");
        }
    }


}
