package com.lixiang.hitravel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lixiang.hitravel.domain.Area;
import com.lixiang.hitravel.domain.City;
import com.lixiang.hitravel.domain.Province;
import com.lixiang.hitravel.domain.Street;
import com.lixiang.hitravel.exception.GlobalException;
import com.lixiang.hitravel.mapper.AreaMapper;
import com.lixiang.hitravel.mapper.CityMapper;
import com.lixiang.hitravel.mapper.ProvinceMapper;
import com.lixiang.hitravel.mapper.StreetMapper;
import com.lixiang.hitravel.result.CodeMsg;
import com.lixiang.hitravel.result.Result;
import com.lixiang.hitravel.service.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhang
 * @date 2020-01-28
 */
@Service
public class AreaServiceImpl implements AreaService {

    private static final Logger log = LoggerFactory.getLogger(AreaServiceImpl.class);

    @Autowired
    private ProvinceMapper provinceMapper;

    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private AreaMapper areaMapper;

    @Autowired
    private StreetMapper streetMapper;

    @Override
    public Result findAllProvince(Integer pageNo, Integer pageSize) {
        try {
            IPage<Province> page = new Page<>(pageNo, pageSize);
            QueryWrapper<Province> wrapper = new QueryWrapper<>();
            Province province = new Province();
            province.setDataState(0);
            wrapper.setEntity(province);
            return Result.success(provinceMapper.selectPage(page, wrapper));
        } catch (GlobalException e) {
            log.error("查询全部省份信息失败：" + e.getMessage());
            return Result.error(CodeMsg.SERVER_ERROR, "查询全部省份信息失败：" + e.getMessage());
        }

    }

    @Override
    public Result findAllCityByPCode(String provinceCode, Integer pageNo, Integer pageSize) {
        try {
            IPage<City> page = new Page<>(pageNo, pageSize);
            QueryWrapper<City> wrapper = new QueryWrapper<>();
            City city = new City();
            city.setProvinceCode(provinceCode);
            wrapper.setEntity(city);
            return Result.success(cityMapper.selectPage(page, wrapper));
        } catch (GlobalException e) {
            log.error("查询市级信息失败：" + e.getMessage());
            return Result.error(CodeMsg.SERVER_ERROR, "查询市级信息失败：" + e.getMessage());
        }
    }

    @Override
    public Result findAllAreaByCCode(String cityCode, Integer pageNo, Integer pageSize) {
        try {
            IPage<Area> page = new Page<>(pageNo, pageSize);
            QueryWrapper<Area> wrapper = new QueryWrapper<>();
            Area area = new Area();
            area.setCityCode(cityCode);
            wrapper.setEntity(area);
            return Result.success(areaMapper.selectPage(page, wrapper));
        } catch (GlobalException e) {
            log.error("查询区级信息失败：" + e.getMessage());
            return Result.error(CodeMsg.SERVER_ERROR, "查询区级信息失败：" + e.getMessage());
        }
    }

    @Override
    public Result findAllStreetByACode(String areaCode, Integer pageNo, Integer pageSize) {
        try {
            IPage<Street> page = new Page<>(pageNo, pageSize);
            QueryWrapper<Street> wrapper = new QueryWrapper<>();
            Street street = new Street();
            street.setAreaCode(areaCode);
            wrapper.setEntity(street);
            return Result.success(streetMapper.selectPage(page, wrapper));
        } catch (GlobalException e) {
            log.error("查询街道信息失败：" + e.getMessage());
            return Result.error(CodeMsg.SERVER_ERROR, "查询街道信息失败：" + e.getMessage());
        }
    }
}
