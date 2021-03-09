package com.levi.mall.service;

import com.levi.mall.vo.CartVO;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @auther Levi
 * @Date: 2021/03/08/16:09
 * @Description:
 */
public interface CartService {

    List<CartVO> addCart(Integer productId, Integer count,Integer userId);

    List<CartVO> cartVOList(Integer userId);

    List<CartVO> cartUpdate(Integer productId, Integer userId, Integer count);

    List<CartVO> cartDelete(Integer productId, Integer userId);

    List<CartVO> selected(Integer productId, Integer userId,Integer selected);

    List<CartVO> selectedAll(Integer userId, Integer selected);
}
