package com.levi.mall.controller;

import com.github.pagehelper.PageInfo;
import com.levi.mall.common.ApiRestResponse;
import com.levi.mall.entity.Order;
import com.levi.mall.request.CreateOrderReq;
import com.levi.mall.service.OrderService;
import com.levi.mall.vo.OrderItemVO;
import com.levi.mall.vo.OrderVO;
import io.swagger.annotations.Api;
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
    @ApiOperation("订单详情")
    @GetMapping("/detail")
    public ApiRestResponse detail(@RequestParam String orderNo) {
        OrderVO orderVO = orderService.detail(orderNo);
        return ApiRestResponse.success(orderVO);
    }

    @ApiOperation("订单列表")
    @GetMapping("/list")
    public ApiRestResponse list(@RequestParam Integer pageNum,@RequestParam Integer pageSize) {
        PageInfo pageInfo = orderService.list(pageNum, pageSize);
        return ApiRestResponse.success(pageInfo);
    }

    @ApiOperation("取消订单")
    @GetMapping("/cancel")
    public ApiRestResponse cancel(String orderNo) {
        orderService.cancel(orderNo);
        return ApiRestResponse.success();
    }

    @ApiOperation("生成订单支付二维码")
    @PostMapping("/qrCode")
    public ApiRestResponse qrCode(String orderNo) {
        String pngadress = orderService.qrCode(orderNo);
        return ApiRestResponse.success(pngadress);
    }

    @ApiOperation("订单支付接口")
    @GetMapping("/pay")
    public ApiRestResponse pay(@RequestParam String orderNo) {
        orderService.orderPay(orderNo);
        return ApiRestResponse.success();
    }

    @ApiOperation("订单完结")
    @PostMapping("/finish")
    public ApiRestResponse finish(String orderNo) {
        orderService.finish(orderNo);
        return ApiRestResponse.success();
    }
}
