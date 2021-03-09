package com.levi.mall.service.impl;

import com.levi.mall.common.Constant;
import com.levi.mall.dao.CartMapper;
import com.levi.mall.dao.ProductMapper;
import com.levi.mall.entity.Cart;
import com.levi.mall.entity.Category;
import com.levi.mall.entity.Product;
import com.levi.mall.exception.MallException;
import com.levi.mall.exception.MallExceptionEnum;
import com.levi.mall.service.CartService;
import com.levi.mall.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @auther Levi
 * @Date: 2021/03/08/16:09
 * @Description:
 */
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<CartVO> addCart(Integer productId, Integer count,Integer userId) {

        validProduct(productId,count);
        Cart cart = cartMapper.selectByProductIdAndUserId(productId, userId);
        if (cart == null) {
            Cart newcart = new Cart();
            newcart.setProductId(productId);
            newcart.setQuantity(count);
            newcart.setSelected(Constant.CartSelected.SELECTED);
            newcart.setUserId(userId);
            cartMapper.insertSelective(newcart);

        }else {
            int num = cart.getQuantity()+count;
            cart.setQuantity(num);
            cartMapper.updateByPrimaryKeySelective(cart);
        }
        return this.cartVOList(userId);
    }
    /**
     * 判断要添加的商品是否合法
     */
    private void validProduct(Integer productId, Integer count) {
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null || product.getStatus().equals(Constant.ProductStatus.NOT_SELL)) {
            throw new MallException(MallExceptionEnum.NOT_SELL);
        }
        if (product.getStock()<count) {
            throw new MallException(MallExceptionEnum.NOT_ENOUGH);
        }
    }

    @Override
    public List<CartVO> cartVOList(Integer userId) {
        List<CartVO> cartVOlist = cartMapper.selectCartVOList(userId);
        for (CartVO cartVO : cartVOlist) {
            cartVO.setTotalPrice(cartVO.getQuantity()*cartVO.getProductPrice());
        }
        return cartVOlist;
    }

    @Override
    public List<CartVO> cartUpdate(Integer productId, Integer userId, Integer count) {
        System.out.println("productId"+productId+"userId:"+userId);
        Cart cart = cartMapper.selectByProductIdAndUserId(productId, userId);
        System.out.println("cart: "+cart);
        if (cart == null) {
            throw new MallException(MallExceptionEnum.UPDATE_FAILED);
        }else{
            cart.setQuantity(count);
            cart.setSelected(1);
            cartMapper.updateByPrimaryKeySelective(cart);

        }
        return this.cartVOList(userId);
    }

    @Override
    public List<CartVO> cartDelete(Integer productId, Integer userId) {
        System.out.println("productId"+productId+"userId:"+userId);
        Cart cart = cartMapper.selectByProductIdAndUserId(productId, userId);
        if (cart == null) {
            throw new MallException(MallExceptionEnum.DELETE_FAILED);
        }else{
            cartMapper.deleteByPrimaryKey(cart.getId());
        }
        return this.cartVOList(userId);
    }

    @Override
    public List<CartVO> selected(Integer productId, Integer userId,Integer selected) {
        Cart cart = cartMapper.selectByProductIdAndUserId(productId, userId);
        if (cart == null) {
            throw new MallException(MallExceptionEnum.UPDATE_FAILED);
        }else{
            cart.setSelected(selected);
            int count = cartMapper.updateSelectedOrNot(userId,productId,selected);

        }
        return this.cartVOList(userId);
    }

    @Override
    public List<CartVO> selectedAll( Integer userId,Integer selected) {
        List<Cart> cartList = cartMapper.selectByUserId(userId);
        //先判断购物车中的商品是否为空
        if (!CollectionUtils.isEmpty(cartList)) {
            int count = cartMapper.updateSelectedOrNot(userId,null,selected);
            if (count == 0) {
                throw new MallException(MallExceptionEnum.UPDATE_FAILED);
            }
        }else {
            throw new  MallException(MallExceptionEnum.CART_IS_NULL);
        }
        return this.cartVOList(userId);
    }
}
