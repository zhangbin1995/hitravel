package com.lixiang.hitravel.controller;

import com.lixiang.hitravel.domain.OrderDetail;
import com.lixiang.hitravel.dto.OrderDetailDto;
import com.lixiang.hitravel.dto.OrderDto;
import com.lixiang.hitravel.result.Result;
import com.lixiang.hitravel.util.MyBeanUtils;
import com.lixiang.hitravel.validator.UserLoginToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author binzhang
 * @date 2020-02-10
 */
@RestController
@Api(value = "订单管理", tags = "订单管理")
@RequestMapping(value = "/order")
public class OrderController {

    private final static Logger log = LoggerFactory.getLogger(OrderController.class);

    @ApiOperation(value = "新增订单", notes = "新增订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderDeltails", value = "订单项", required = true, paramType = "query", dataType = "Integer", defaultValue = "[{1, 1, 2020-02-02, 2}]")
    })
    @PostMapping(value = "/add")
    @UserLoginToken
    public Result add(HttpServletRequest httpServletRequest, @Valid @RequestBody OrderDto orderDto) {
        List<OrderDetail> list = new ArrayList<>();
        List<OrderDetailDto> dtoList = orderDto.getOrderdetailList();
        for (OrderDetailDto dto : dtoList) {
            list.add(MyBeanUtils.dtoToVo(dto, OrderDetail.class));
        }
        String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
        return Result.success("111");
    }
}
