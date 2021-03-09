package com.levi.mall.service;

import com.levi.mall.entity.Order;
import com.levi.mall.request.CreateOrderReq;

/**
 * Created with IntelliJ IDEA.
 *
 * @auther Levi
 * @Date: 2021/03/09/14:55
 * @Description:
 */
public interface OrderService {

    /**
     * 创建订单
     * @param createOrderReq
     * @return
     */
    Order createOrder(CreateOrderReq createOrderReq);
}
