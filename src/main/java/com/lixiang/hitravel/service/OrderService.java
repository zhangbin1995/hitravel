package com.lixiang.hitravel.service;

import com.lixiang.hitravel.domain.OrderDetail;
import com.lixiang.hitravel.result.Result;

import java.util.List;

/**
 * @author zhang
 * @date 2020-03-07
 */
public interface OrderService {

    Result addOrder(String token, List<OrderDetail> list);

    Result cancelOrder(Integer orderId);

    Result myOrder(String token, Integer status, Integer orderType, Integer pageNo, Integer pageSize);

    Result myOrderForStore(Integer hotelOrScenicId, Integer status, Integer orderType, Integer pageNo, Integer pageSize);
}
