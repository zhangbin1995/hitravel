package com.lixiang.hitravel.controller;

import com.lixiang.hitravel.domain.Hotel;
import com.lixiang.hitravel.dto.HotelDto;
import com.lixiang.hitravel.result.Result;
import com.lixiang.hitravel.service.HotelService;
import com.lixiang.hitravel.util.MyBeanUtils;
import com.lixiang.hitravel.util.WebLog;
import com.lixiang.hitravel.validator.UserLoginToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author zhang
 * @date 2020-01-21
 */
@RestController
@Api(value = "酒店管理", tags = "酒店管理")
@RequestMapping(value = "/hotel")
public class HotelController {

    private final static Logger log = LoggerFactory.getLogger(HotelController.class);

    @Autowired
    private HotelService hotelService;

    @ApiOperation(value = "酒店注册", notes = "酒店注册")
    @PostMapping(value = "/add")
    @UserLoginToken
    public Result add(HttpServletRequest httpServletRequest, @Valid @RequestBody HotelDto hotelDto) {
        Hotel hotel = MyBeanUtils.dtoToVo(hotelDto, Hotel.class);
        String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
        return hotelService.save(token,hotel);
    }

    @ApiOperation(value = "修改酒店信息", notes = "修改酒店信息")
    @PostMapping(value = "/update")
    public Result update(@Valid @RequestBody HotelDto hotelDto) {
        Hotel hotel = MyBeanUtils.dtoToVo(hotelDto, Hotel.class);
        return hotelService.updateHotelInfo(hotel);
    }

    @ApiOperation(value = "审核酒店（管理员）", notes = "审核酒店（管理员）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "hotelId", value = "酒店id", paramType = "query", dataType = "String", defaultValue = "酒店id")
    })
    @GetMapping(value = "/passVerify")
    @UserLoginToken
    public Result passVerify(@RequestParam Integer hotelId) {
        return hotelService.passVerify(hotelId);
    }

    @ApiOperation(value = "删除酒店", notes = "删除酒店")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "hotelId", value = "酒店id", paramType = "query", dataType = "String", defaultValue = "酒店id")
    })
    @GetMapping(value = "/delete")
    public Result delete(@RequestParam Integer hotelId) {
        return hotelService.delete(hotelId);
    }

    @ApiOperation(value = "我的酒店列表（商家酒店）", notes = "我的酒店列表（商家酒店）")
    @GetMapping(value = "/queryMyHotel")
    @UserLoginToken
    public Result queryMyHotel(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
        return hotelService.queryMyHotel(token);
    }

    @ApiOperation(value = "根据主键id查看酒店信息", notes = "根据主键id查看酒店信息")
    @PostMapping(value = "/queryById")
    public Result queryById(@RequestParam Integer hotelId) {
        return hotelService.queryById(hotelId);
    }

    @ApiOperation(value = "分页查询酒店(管理员)", notes = "分页查询酒店，如果传入街道编码，则查出来的是某街道下的酒店，还可以根据状态查询，0 未审核 1 审核 2 删除的")
    @PostMapping(value = "/queryByPageForAdmin")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "streetCode", value = "街道编码", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "status", value = "状态", paramType = "query", dataType = "Integer", defaultValue = "状态 0 未审核 1 审核 2 删除的"),
            @ApiImplicitParam(name = "pageNo", value = "页码", required = true, paramType = "query", dataType = "Integer", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数", required = true, paramType = "query", dataType = "Integer", defaultValue = "10")
    })
    public Result queryByPage(@RequestParam(required = false) String streetCode, @RequestParam(required = false) Integer status, @RequestParam Integer pageNo, @RequestParam Integer pageSize) {
        return hotelService.queryByPageForAdmin(streetCode, status, pageNo, pageSize);
    }

    @ApiOperation(value = "分页查询酒店(游客)", notes = "根据所在城市和酒店名模糊(选填)分页查询酒店")
//    @WebLog
    @GetMapping(value = "/queryByPage")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cityCode", value = "城市编码", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "hotelName", value = "酒店名", paramType = "query", dataType = "String", defaultValue = "酒店名"),
            @ApiImplicitParam(name = "pageNo", value = "页码", required = true, paramType = "query", dataType = "Integer", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数", required = true, paramType = "query", dataType = "Integer", defaultValue = "10")
    })
    public Result queryByPage(@RequestParam(required = false) String cityCode, @RequestParam(required = false) String hotelName, @RequestParam Integer pageNo, @RequestParam Integer pageSize) {
        return hotelService.queryByPage(cityCode, hotelName, pageNo, pageSize);
    }

}
