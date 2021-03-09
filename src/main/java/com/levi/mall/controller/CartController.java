package com.levi.mall.controller;

import com.levi.mall.common.ApiRestResponse;
import com.levi.mall.filter.UserFilter;
import com.levi.mall.service.CartService;
import com.levi.mall.vo.CartVO;
import com.levi.mall.vo.CategoryVO;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @auther Levi
 * @Date: 2021/03/08/15:41
 * @Description: 购物车Controller
 */
@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ApiRestResponse add(@RequestParam Integer productId, @RequestParam Integer count) {
        List<CartVO> listVO = cartService.addCart(productId,count,UserFilter.currentUser.getId());
        return ApiRestResponse.success(listVO);
    }
    @PostMapping("/list")
    public ApiRestResponse cartList() {
        List<CartVO> listVO = cartService.cartVOList(UserFilter.currentUser.getId());
        return ApiRestResponse.success(listVO);
    }

    @PostMapping("/update")
    public ApiRestResponse update(@RequestParam Integer productId,@RequestParam Integer count) {
        List<CartVO> cartVOS = cartService.cartUpdate(productId, UserFilter.currentUser.getId(),count);
        return ApiRestResponse.success(cartVOS);
    }
    @PostMapping("/delete")
    public ApiRestResponse delete(@RequestParam Integer productId) {
        List<CartVO> cartVOS = cartService.cartDelete(productId, UserFilter.currentUser.getId());
        return ApiRestResponse.success(cartVOS);
    }

    @PostMapping("/selected")
    public ApiRestResponse selectCartOrNot( Integer productId,@RequestParam Integer selected) {
        List<CartVO> cartVOS = cartService.selected(productId, UserFilter.currentUser.getId(), selected);
        return ApiRestResponse.success(cartVOS);
    }

    @PostMapping("/selectedAll")
    public ApiRestResponse selectCartOrNotAll( @RequestParam Integer selected) {
        List<CartVO> cartVOS = cartService.selectedAll(UserFilter.currentUser.getId(), selected);
        return ApiRestResponse.success(cartVOS);
    }
}
