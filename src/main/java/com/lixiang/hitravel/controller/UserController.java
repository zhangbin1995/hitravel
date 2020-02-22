package com.lixiang.hitravel.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.lixiang.hitravel.domain.User;
import com.lixiang.hitravel.dto.UserDto;
import com.lixiang.hitravel.result.CodeMsg;
import com.lixiang.hitravel.result.Result;
import com.lixiang.hitravel.service.UserService;
import com.lixiang.hitravel.util.MyBeanUtils;
import com.lixiang.hitravel.validator.UserLoginToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author binzhang
 * @date 2020-01-16
 */

@Api(value = "用户管理", tags = "用户管理")
@RestController
@RequestMapping(value = "/user")
@Slf4j
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @ApiOperation(value = "测试", notes = "测试")
    @GetMapping(value = "/test")
    @UserLoginToken
    public Result test() {

        return Result.success("测试成功");
    }

    @ApiOperation(value = "用户注册", notes = "用户注册")
    @PostMapping(value = "/add")
    public Result add(@Valid @RequestBody UserDto userDto) {
        User user = MyBeanUtils.dtoToVo(userDto, User.class);
        return userService.save(user);
    }

    @ApiOperation(value = "用户修改", notes = "修改用户信息")
    @PostMapping(value = "/update")
    public Result update(@Valid @RequestBody UserDto userDto) {
        User user = MyBeanUtils.dtoToVo(userDto, User.class);
        return userService.updateUserInfo(user);
    }

    @ApiOperation(value = "用户审核(用于商家用户)", notes = "修改用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户Id", required = true, paramType = "query", dataType = "Integer")
    })
    @PostMapping(value = "/verify")
    public Result verify(@RequestParam Integer userId) {
        return userService.passVerify(userId);
    }

    @ApiOperation(value = "用户登录校验", notes = "用户登录校验")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "type", value = "用户类型，0-管理员，1-普通用户，2-商家", required = true, paramType = "query", dataType = "Interger")
    })
    @PostMapping(value = "/login")
    public Result login(@RequestParam String username, @RequestParam String password, @RequestParam Integer type) {
        return userService.login(username, password, type);
    }

    @ApiOperation(value = "用户删除(禁用)", notes = "逻辑删除，即修改状态(管理员使用)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户Id", required = true, paramType = "query", dataType = "Integer")
    })
    @PostMapping(value = "/delete")
    public Result delete(@RequestParam Integer userId) {

        return userService.delete(userId);
    }

    @ApiOperation(value = "分页查询用户列表", notes = "分页查询用户列表")
    @RequestMapping(value = "/findUserByPage",method = RequestMethod.POST)
    public Result findUserByPage(@RequestParam Integer pageNo, @RequestParam Integer pageSize){
        IPage<User> page = new Page<>(pageNo, pageSize);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        User user = new User();
//        user.setUserType(1);
        wrapper.setEntity(user);
        return userService.findUserByPage(page,wrapper);
    }


}
