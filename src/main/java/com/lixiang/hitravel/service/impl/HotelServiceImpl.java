package com.lixiang.hitravel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.lixiang.hitravel.domain.Hotel;
import com.lixiang.hitravel.domain.User;
import com.lixiang.hitravel.domain.UserHotelRef;
import com.lixiang.hitravel.enums.HotelEnum;
import com.lixiang.hitravel.exception.GlobalException;
import com.lixiang.hitravel.mapper.HotelMapper;
import com.lixiang.hitravel.mapper.UserHotelRefMapper;
import com.lixiang.hitravel.redis.RedisService;
import com.lixiang.hitravel.redis.UserKey;
import com.lixiang.hitravel.result.CodeMsg;
import com.lixiang.hitravel.result.Result;
import com.lixiang.hitravel.service.HotelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author zhang
 * @date 2020-01-21
 */
@Service
@SuppressWarnings("all")
public class HotelServiceImpl implements HotelService {

    private final static Logger log = LoggerFactory.getLogger(HotelServiceImpl.class);

    @Autowired
    private HotelMapper hotelMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserHotelRefMapper userHotelRefMapper;


    /**
     * 1. 保存酒店
     * 2. 保存用户酒店关联信息
     */
    @Override
    @Transactional
    public Result save(String token, Hotel hotel) {
        try {
            hotel.setCreateTime(new Date());
            // 注册时酒店状态为不可用，需要等待通过审核
            hotel.setStatus(HotelEnum.STATUS_INVALID.getCode());
            int res = hotelMapper.insert(hotel);
            QueryWrapper<Hotel> wrapper = new QueryWrapper<>();
            wrapper.setEntity(hotel);
            Hotel hotel1 = hotelMapper.selectOne(wrapper);
            UserHotelRef ref = new UserHotelRef();
            ref.setHotelId(hotel1.getHotelId());
            // redis中取出用户信息
            User user = redisService.get(UserKey.token, token, User.class);
            if (user == null) {
                throw new GlobalException("当前用户为空");
            }
            ref.setUserId(user.getUserId());
            // 默认正常状态
            ref.setStatus(1);
            int res1 = userHotelRefMapper.insert(ref);
            // 同时添加酒店用户关联表信息
            if (res > 0 && res1 > 0) {
                return Result.success("酒店注册成功");
            } else {
                throw new GlobalException("酒店注册失败");
            }
        } catch (GlobalException e) {
            throw new GlobalException("保存酒店信息错误，错误信息：" + e.getMessage());
        }
    }

    @Override
    public Result delete(Integer hotelId) {
        try {
            int res = hotelMapper.deleteById(hotelId);
            if (res > 0) {
                return Result.success("删除酒店成功");
            } else {
                return Result.error(CodeMsg.ERROR, "删除酒店失败");
            }
        } catch (GlobalException e) {
            throw new GlobalException("删除酒店失败，错误信息：" + e.getMessage());
        }
    }

    @Override
    public Result passVerify(Integer hotelId) {
        try {
            Hotel hotel = hotelMapper.selectById(hotelId);
            hotel.setStatus(1);
            int res = hotelMapper.updateById(hotel);
            if (res > 0) {
                return Result.success("酒店审核通过");
            } else {
                return Result.error(CodeMsg.ERROR, "酒店审核失败");
            }
        } catch (GlobalException e) {
            return Result.error(CodeMsg.SERVER_ERROR, "酒店审核失败，错误信息：" + e.getMessage());
        }
    }

    @Override
    public Result updateHotelInfo(Hotel hotel) {
        try {
            int res = hotelMapper.updateById(hotel);
            if (res > 0) {
                return Result.success("酒店信息修改成功");
            } else {
                return Result.error(CodeMsg.ERROR, "酒店信息修改失败");
            }
        } catch (GlobalException e) {
            return Result.error(CodeMsg.SERVER_ERROR, "酒店信息修改失败，错误信息：" + e.getMessage());
        }
    }

    @Override
    public Result findHotelByPage(IPage<Hotel> page, QueryWrapper<Hotel> wrapper) {
        return null;
    }

    @Override
    public Result queryById(Integer hotelId) {
        try {
            Hotel hotel = hotelMapper.selectById(hotelId);
            if (hotel != null) {
                return Result.success(hotel);
            } else {
                return Result.error(CodeMsg.ERROR, "查询酒店信息失败");
            }
        } catch (GlobalException e) {
            return Result.error(CodeMsg.SERVER_ERROR, "查询酒店信息失败，错误信息：" + e.getMessage());
        }
    }

    @Override
    public Result queryByPageForAdmin(String streetCode, Integer status, Integer pageNo, Integer pageSize) {
        try {
            IPage<Hotel> page = new Page<>(pageNo, pageSize);
            QueryWrapper<Hotel> wrapper = new QueryWrapper<>();
            Hotel hotel = new Hotel();
            if (!Strings.isNullOrEmpty(streetCode)) {
                hotel.setStreetCode(streetCode);
            }
            if (status != null) {
                hotel.setStatus(status);
            }
            wrapper.setEntity(hotel);
            return Result.success(hotelMapper.selectPage(page, wrapper));

        } catch (GlobalException e) {
            return Result.error(CodeMsg.SERVER_ERROR, "查询酒店信息失败，错误信息：" + e.getMessage());
        }
    }

    @Override
    public Result queryMyHotel(String token) {
        try {
            User user = redisService.get(UserKey.token, token, User.class);
            if (user == null) {
                throw new GlobalException("当前用户为空");
            }
            List<Hotel> list = hotelMapper.queryMyHotel(user.getUserId());
            if (list != null || list.size() > 0) {
                return Result.success(list);
            }
            return Result.success("该用户下无酒店");
        } catch (GlobalException e) {
            log.error("查询失败：" + e.getMessage());
            throw new GlobalException("查询酒店信息失败，错误信息：" + e.getMessage());
        }
    }

    @Override
    public Result queryByPage(String cityCode, String hotelName, Integer pageNo, Integer pageSize) {
        try {
            IPage<Hotel> page = new Page<>(pageNo, pageSize);
            QueryWrapper<Hotel> wrapper = new QueryWrapper<>();
            Hotel hotel = new Hotel();
            hotel.setStatus(HotelEnum.STATUS_VALID.getCode());
            if (!Strings.isNullOrEmpty(cityCode)) {
                hotel.setCityCode(cityCode);
            }
            if (!Strings.isNullOrEmpty(hotelName)) {
                wrapper.like("hotel_name", hotelName);
            }
            wrapper.setEntity(hotel);
            return Result.success(hotelMapper.selectPage(page, wrapper));

        } catch (GlobalException e) {
            return Result.error(CodeMsg.SERVER_ERROR, "查询酒店信息失败，错误信息：" + e.getMessage());
        }
    }
}
