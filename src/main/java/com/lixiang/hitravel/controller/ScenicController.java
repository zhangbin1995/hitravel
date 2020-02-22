package com.lixiang.hitravel.controller;

import com.lixiang.hitravel.domain.Hotel;
import com.lixiang.hitravel.domain.Scenic;
import com.lixiang.hitravel.dto.ScenicDto;
import com.lixiang.hitravel.result.Result;
import com.lixiang.hitravel.service.ScenicService;
import com.lixiang.hitravel.util.MyBeanUtils;
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
 * @author binzhang
 * @date 2020-02-06
 */
@RestController
@Api(value = "景区管理", tags = "景区管理")
@RequestMapping(value = "/scenic")
public class ScenicController {
    private final static Logger log = LoggerFactory.getLogger(ScenicController.class);

    @Autowired
    private ScenicService scenicService;

    @ApiOperation(value = "景区注册", notes = "景区注册")
    @PostMapping(value = "/add")
    @UserLoginToken
    public Result add(HttpServletRequest httpServletRequest, @Valid @RequestBody ScenicDto scenicDto) {
        Scenic scenic = MyBeanUtils.dtoToVo(scenicDto, Scenic.class);
        String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
        return scenicService.save(token,scenic);
    }

    @ApiOperation(value = "修改景区信息", notes = "修改景区信息")
    @PostMapping(value = "/update")
    @UserLoginToken
    public Result update(@Valid @RequestBody ScenicDto scenicDto) {
        Scenic scenic = MyBeanUtils.dtoToVo(scenicDto, Scenic.class);
        return scenicService.update(scenic);
    }

    @ApiOperation(value = "删除景区", notes = "删除景区")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "scenicId", value = "景区id", paramType = "query", dataType = "String", defaultValue = "景区id")
    })
    @GetMapping(value = "/delete")
    @UserLoginToken
    public Result delete(@RequestParam Integer scenicId) {
        return scenicService.delete(scenicId);
    }

    @ApiOperation(value = "审核景区（管理员）", notes = "审核景区（管理员）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "scenicId", value = "景区id", paramType = "query", dataType = "String", defaultValue = "景区id")
    })
    @GetMapping(value = "/passVerify")
    @UserLoginToken
    public Result passVerify(@RequestParam Integer scenicId) {
        return scenicService.passVerify(scenicId);
    }

    @ApiOperation(value = "根据主键id查看景区信息", notes = "根据主键id查看景区信息")
    @PostMapping(value = "/queryById")
    public Result queryById(@RequestParam Integer scenicId) {
        return scenicService.queryById(scenicId);
    }

    @ApiOperation(value = "分页查询景区(游客，管理员)", notes = "分页查询景区，如果传入市编码，则查出来的是某市下的景区，还可以根据状态查询，0 未审核 1 审核 2 删除的")
    @PostMapping(value = "/queryByPage")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cityCode", value = "市编码", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "status", value = "状态", paramType = "query", dataType = "Integer", defaultValue = "状态 0 未审核 1 审核 2 删除的"),
            @ApiImplicitParam(name = "pageNo", value = "页码", required = true, paramType = "query", dataType = "Integer", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数", required = true, paramType = "query", dataType = "Integer", defaultValue = "10")
    })
    public Result queryByPage(@RequestParam(required = false) String cityCode, @RequestParam(required = false) Integer status, @RequestParam Integer pageNo, @RequestParam Integer pageSize) {
        return scenicService.queryByPage(cityCode, status, pageNo, pageSize);
    }

    @ApiOperation(value = "我的景区列表（景区商家）", notes = "我的景区列表（景区商家）")
    @GetMapping(value = "/queryMyScenic")
    @UserLoginToken
    public Result queryMyScenic(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
        return scenicService.queryMyScenic(token);
    }
}
