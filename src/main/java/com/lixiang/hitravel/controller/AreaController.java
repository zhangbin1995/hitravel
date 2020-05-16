package com.lixiang.hitravel.controller;

import com.lixiang.hitravel.result.Result;
import com.lixiang.hitravel.service.AreaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhang
 * @date 2020-01-16
 */

@Api(value = "地区管理", tags = "地区管理")
@RestController
@RequestMapping(value = "/area")
@Slf4j
public class AreaController {

    private final static Logger logger = LoggerFactory.getLogger(AreaController.class);

    @Autowired
    private AreaService areaService;

    @ApiOperation(value = "查询所有省份", notes = "查询所有省份，分页是多余的，页码传1，pageSize传100及以上均可")
    @RequestMapping(value = "/queryAllProvince",method = RequestMethod.POST)
    public Result queryAllProvince(@RequestParam Integer pageNo, @RequestParam Integer pageSize){
        return areaService.findAllProvince(pageNo,pageSize);
    }

    @ApiOperation(value = "所有省下所有市", notes = "根据省份Code查询省下所有市")
    @RequestMapping(value = "/queryCitysByProvinceCode",method = RequestMethod.POST)
    public Result queryCitysByProvinceCode(@RequestParam String provinceCode, @RequestParam Integer pageNo, @RequestParam Integer pageSize){
        return areaService.findAllCityByPCode(provinceCode, pageNo, pageSize);
    }

    @ApiOperation(value = "所有市下所有区", notes = "根据市Code查询市下所有区")
    @RequestMapping(value = "/queryAreaByCityCode",method = RequestMethod.POST)
    public Result queryAreaByCityCode(@RequestParam String cityCode, @RequestParam Integer pageNo, @RequestParam Integer pageSize){
        return areaService.findAllAreaByCCode(cityCode, pageNo, pageSize);
    }

    @ApiOperation(value = "所有区下所有街道", notes = "根据区Code查询区下所有街道")
    @RequestMapping(value = "/queryStreetByAreaCode",method = RequestMethod.POST)
    public Result queryStreetByAreaCode(@RequestParam String areaCode, @RequestParam Integer pageNo, @RequestParam Integer pageSize){
        return areaService.findAllStreetByACode(areaCode, pageNo, pageSize);
    }


}
