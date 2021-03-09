package com.levi.mall.controller;

import com.levi.mall.common.ApiRestResponse;
import com.levi.mall.dao.OrderMapper;
import com.levi.mall.entity.Order;
import com.levi.mall.request.CreateOrderReq;
import com.levi.mall.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @auther Levi
 * @Date: 2021/03/09/14:47
 * @Description:
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation("创建订单")
    @PostMapping("/create")
    public ApiRestResponse create(@RequestBody CreateOrderReq createOrderReq) {
        Order order = orderService.createOrder(createOrderReq);
        return ApiRestResponse.success(order);
    }
}
