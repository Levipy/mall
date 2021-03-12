package com.levi.mall.controller;

import com.github.pagehelper.PageInfo;
import com.levi.mall.common.ApiRestResponse;
import com.levi.mall.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @auther Levi
 * @Date: 2021/03/10/16:11
 * @Description:
 */
@RestController
public class AdminOrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation("后台管理订单页面")
    @PostMapping("admin/order/list")
    public ApiRestResponse adminList(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageInfo pageInfo = orderService.adminList(pageNum, pageSize);
        return ApiRestResponse.success(pageInfo);
    }

    @ApiOperation("管理员发货接口")
    @PostMapping("/admin/order/delivery")
    public ApiRestResponse delivery(String orderNo) {
        orderService.deliveryOrder(orderNo);
        return ApiRestResponse.success();
    }


}
