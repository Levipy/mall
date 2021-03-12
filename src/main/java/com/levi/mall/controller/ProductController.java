package com.levi.mall.controller;

import com.github.pagehelper.PageInfo;
import com.levi.mall.common.ApiRestResponse;
import com.levi.mall.entity.Product;
import com.levi.mall.request.ListForCustomerProductReq;
import com.levi.mall.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 *
 * @auther Levi
 * @Date: 2021/03/07/22:18
 * @Description:
 */
@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @ApiOperation("商品详情接口")
    @GetMapping("/product/detail")
    public ApiRestResponse detail(@RequestParam("id") Integer id) {
        Product product = productService.detail(id);
        return ApiRestResponse.success(product);
    }

    @ApiOperation("用户商品列表展示")
    @GetMapping("/product/list")
    public ApiRestResponse listForCustomer(ListForCustomerProductReq listForCustomerProductReq) {
        PageInfo pageInfo = productService.productListForCustomer(listForCustomerProductReq);
        return ApiRestResponse.success(pageInfo);
    }
}
