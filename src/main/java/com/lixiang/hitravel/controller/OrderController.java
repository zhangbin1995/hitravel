package com.lixiang.hitravel.controller;

import com.lixiang.hitravel.domain.OrderDetail;
import com.lixiang.hitravel.dto.OrderDetailDto;
import com.lixiang.hitravel.dto.OrderDto;
import com.lixiang.hitravel.result.Result;
import com.lixiang.hitravel.service.OrderService;
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

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "新增订单", notes = "新增订单")
    @PostMapping(value = "/add")
    @UserLoginToken
    public Result add(HttpServletRequest request, @Valid @RequestBody OrderDto orderDto) {
        List<OrderDetail> list = new ArrayList<>();
        for (int i = 0 ; i < orderDto.getIds().length ; i ++) {
            OrderDetail detail = new OrderDetail();
            detail.setRoomOrTicketId(orderDto.getIds()[i]);
            detail.setOrderType(orderDto.getTypes()[i]);
            detail.setNumber(orderDto.getNums()[i]);
            detail.setBookTime(orderDto.getDates()[i]);
            list.add(detail);
        }
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        return orderService.addOrder(token, list);
    }

    @ApiOperation(value = "取消订单", notes = "取消订单")
    @GetMapping(value = "/cancel")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单编号", paramType = "query", dataType = "Integer")
    })
//    @UserLoginToken
    public Result cancel(@RequestParam Integer orderId) {
        return orderService.cancelOrder(orderId);
    }

    @ApiOperation(value = "我的订单（用户）", notes = "我的订单（用户）")
    @GetMapping(value = "/myOrder")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "订单状态 不传默认全部 0-已预订 1-已取消", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "orderType", value = "订单类型 不传默认全部 0酒店 1景区", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "pageNo", value = "页码", required = true, paramType = "query", dataType = "Integer", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数", required = true, paramType = "query", dataType = "Integer", defaultValue = "10")
    })
    @UserLoginToken
    public Result myOrder(HttpServletRequest request, @RequestParam(required = false) Integer status, @RequestParam(required = false) Integer orderType, @RequestParam Integer pageNo, @RequestParam Integer pageSize) {
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        return orderService.myOrder(token, status, orderType, pageNo, pageSize);
    }

    @ApiOperation(value = "我的订单（商家）", notes = "我的订单（商家）")
    @GetMapping(value = "/myOrderForStore")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "hotelOrScenicId", value = "酒店/景区Id", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "status", value = "订单状态 不传默认全部 0-已预订 1-已取消", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "orderType", value = "订单类型 不传默认全部 0酒店 1景区", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "pageNo", value = "页码", required = true, paramType = "query", dataType = "Integer", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数", required = true, paramType = "query", dataType = "Integer", defaultValue = "10")
    })
//    @UserLoginToken
    public Result myOrderForStore(@RequestParam Integer hotelOrScenicId, @RequestParam(required = false) Integer status, @RequestParam(required = false) Integer orderType, @RequestParam Integer pageNo, @RequestParam Integer pageSize) {
        return orderService.myOrderForStore(hotelOrScenicId, status, orderType, pageNo, pageSize);
    }


}
