package com.levi.mall.service;

import com.github.pagehelper.PageInfo;
import com.levi.mall.entity.Order;
import com.levi.mall.request.CreateOrderReq;
import com.levi.mall.vo.OrderVO;

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

    OrderVO detail(String orderNo);

    PageInfo list(Integer pageNum, Integer pageSize);

    void cancel(String orderNo);

    String qrCode(String orderNo);

    PageInfo adminList(Integer pageNum, Integer pageSize);

    void orderPay(String orderNo);

    void deliveryOrder(String orderNo);

    void finish(String orderNo);

}
