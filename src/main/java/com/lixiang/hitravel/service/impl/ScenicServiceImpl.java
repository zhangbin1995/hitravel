package com.lixiang.hitravel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.lixiang.hitravel.domain.Hotel;
import com.lixiang.hitravel.domain.Scenic;
import com.lixiang.hitravel.domain.User;
import com.lixiang.hitravel.enums.ScenicEnum;
import com.lixiang.hitravel.exception.GlobalException;
import com.lixiang.hitravel.mapper.ScenicMapper;
import com.lixiang.hitravel.redis.RedisService;
import com.lixiang.hitravel.redis.UserKey;
import com.lixiang.hitravel.result.CodeMsg;
import com.lixiang.hitravel.result.Result;
import com.lixiang.hitravel.service.ScenicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author zhang
 * @date 2020-02-06
 */
@Service
public class ScenicServiceImpl implements ScenicService {

    private final static Logger log = LoggerFactory.getLogger(ScenicServiceImpl.class);

    @Autowired
    private ScenicMapper scenicMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public Result save(String token, Scenic scenic) {
        try {
            scenic.setCreateTime(new Date());
            // 注册时景区状态为不可用，需要等待通过审核
            scenic.setStatus(ScenicEnum.STATUS_INVALID.getCode());

            // redis中取出用户信息
            User user = redisService.get(UserKey.token, token, User.class);
            if (user == null) {
                throw new GlobalException("当前无登录用户");
            }
            scenic.setUserId(user.getUserId());
            int res = scenicMapper.insert(scenic);
            if (res > 0) {
                return Result.success("景区注册成功");
            } else {
                throw new GlobalException("景区注册失败");
            }
        } catch (GlobalException e) {
            throw new GlobalException("保存景区信息错误，错误信息：" + e.getMessage());
        }
    }

    @Override
    public Result delete(Integer scenicId) {
        try {
            int res = scenicMapper.deleteById(scenicId);
            if (res > 0) {
                return Result.success("删除景区成功");
            } else {
                throw new GlobalException("删除景区失败");
            }
        } catch (GlobalException e) {
            throw new GlobalException("删除景区信息错误，错误信息：" + e.getMessage());
        }
    }

    @Override
    public Result passVerify(Integer scenicId) {
        try {
            Scenic scenic = scenicMapper.selectById(scenicId);
            scenic.setStatus(1);
            int res = scenicMapper.updateById(scenic);
            if (res > 0) {
                return Result.success("景区审核通过");
            } else {
                return Result.error(CodeMsg.ERROR, "景区审核失败");
            }
        } catch (GlobalException e) {
            return Result.error(CodeMsg.SERVER_ERROR, "景区审核失败，错误信息：" + e.getMessage());
        }
    }

    @Override
    public Result update(Scenic scenic) {
        try {
            int res = scenicMapper.updateById(scenic);
            if (res > 0) {
                return Result.success("景区信息修改成功");
            } else {
                return Result.error(CodeMsg.ERROR, "景区信息修改失败");
            }
        } catch (GlobalException e) {
            return Result.error(CodeMsg.SERVER_ERROR, "景区信息修改失败，错误信息：" + e.getMessage());
        }
    }


    @Override
    public Result queryById(Integer scenicId) {
        try {
            Scenic scenic = scenicMapper.selectById(scenicId);
            if (scenic != null) {
                return Result.success(scenic);
            } else {
                return Result.error(CodeMsg.ERROR, "未查询到景区信息");
            }
        } catch (GlobalException e) {
            return Result.error(CodeMsg.SERVER_ERROR, "景区信息查询失败，错误信息：" + e.getMessage());
        }
    }

    @Override
    public Result queryByPage(String cityCode, Integer status, Integer pageNo, Integer pageSize) {
        try {
            QueryWrapper<Scenic> wrapper = new QueryWrapper<>();
            Scenic temp = new Scenic();
            if (!Strings.isNullOrEmpty(cityCode)) {
                temp.setCityCode(cityCode);
            }
            if (status != null) {
                temp.setStatus(status);
            }
            wrapper.setEntity(temp);
            IPage<Scenic> page = new Page<>(pageNo, pageSize);
            return Result.success(scenicMapper.selectPage(page, wrapper));

        } catch (GlobalException e) {
            return Result.error(CodeMsg.SERVER_ERROR, "景区信息查询失败，错误信息：" + e.getMessage());
        }
    }

    @Override
    public Result queryMyScenic(String token) {
        try {
            User user = redisService.get(UserKey.token, token, User.class);
            if (user == null) {
                return Result.error(CodeMsg.SERVER_ERROR, "当前用户为空");
            }
            QueryWrapper<Scenic> wrapper = new QueryWrapper<>();
            Scenic temp = new Scenic();
            temp.setUserId(user.getUserId());
            wrapper.setEntity(temp);
            return Result.success(scenicMapper.selectList(wrapper));
        } catch (GlobalException e) {
            return Result.error(CodeMsg.SERVER_ERROR, "景区信息查询失败，错误信息：" + e.getMessage());
        }
    }

}
