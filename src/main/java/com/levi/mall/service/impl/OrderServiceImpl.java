package com.levi.mall.service.impl;

import com.levi.mall.common.Constant;
import com.levi.mall.dao.CartMapper;
import com.levi.mall.dao.OrderItemMapper;
import com.levi.mall.dao.OrderMapper;
import com.levi.mall.dao.ProductMapper;
import com.levi.mall.entity.Order;
import com.levi.mall.entity.OrderItem;
import com.levi.mall.entity.Product;
import com.levi.mall.exception.MallException;
import com.levi.mall.exception.MallExceptionEnum;
import com.levi.mall.filter.UserFilter;
import com.levi.mall.request.CreateOrderReq;
import com.levi.mall.service.OrderService;
import com.levi.mall.util.OrderCodeFactory;
import com.levi.mall.vo.CartVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @auther Levi
 * @Date: 2021/03/09/14:56
 * @Description:
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Order createOrder(CreateOrderReq createOrderReq) {
        //获取用户的id
        Integer userId = UserFilter.currentUser.getId();
        //查询用户已勾选的商品,如果为空报错
        List<CartVO> cartVOList = cartMapper.selectCartVOList(userId);
        List<CartVO> cartSelected = new ArrayList<>();
        for (int i = 0; i < cartVOList.size(); i++) {
            CartVO cartVO =  cartVOList.get(i);
            if (cartVO.getSelected().equals(Constant.CartSelected.SELECTED)) {
                cartSelected.add(cartVO);
            }
        }
        cartVOList = cartSelected;
        if (CollectionUtils.isEmpty(cartVOList)) {
            throw new MallException(MallExceptionEnum.CART_IS_NULL);
        }
        //判断商品是否上架，库存是否够
        validStatusAndStock(cartVOList);
        //扣库存
        cutStock(cartVOList);
        String orderNo = OrderCodeFactory.getOrderCode(userId.longValue());
        //把订单信息放进order_item
        List<OrderItem> orderItemList = cartVOListToOrderItem(cartVOList,orderNo);
        //删除购物车中的已下单商品
        cleanCart(cartVOList);
        //生成订单order
        Order order = new Order();
        BeanUtils.copyProperties(createOrderReq, order);
        order.setOrderNo(orderNo);
        order.setUserId(userId);
        order.setTotalPrice(getTotalPrice(orderItemList));
        order.setOrderStatus(Constant.OrderStatusEnum.NOT_PAID.getCode());

        order.setPostage(0);
        order.setPaymentType(Constant.PayType.ALI_PAY.getPay());

        orderMapper.insertSelective(order);
        for (OrderItem orderItem : orderItemList) {
            orderItemMapper.insertSelective(orderItem);
            throw new MallException(MallExceptionEnum.ANOTHER_ERROR);
        }
        Order return_order = orderMapper.selectByOrderNoAndUserId(orderNo, userId);
        return return_order;
    }

    /**
     * 获取order的totalPrice
     * @param orderItemList
     * @return
     */
    private Integer getTotalPrice(List<OrderItem> orderItemList) {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItemList) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

    /**
     * //删除购物车中的已下单商品
     * @param cartVOList
     */
    private void cleanCart(List<CartVO> cartVOList) {
        for (CartVO cartVO : cartVOList) {
            cartMapper.deleteByPrimaryKey(cartVO.getId());
        }
    }

    /**
     * 把订单商品信息放进订单item
     * @param cartVOList
     */
    private List<OrderItem> cartVOListToOrderItem(List<CartVO> cartVOList,String orderNo) {
        List<OrderItem> orderItemList = new ArrayList<>();
        for (CartVO cartVO : cartVOList) {
            OrderItem orderItem = new OrderItem();
            BeanUtils.copyProperties(cartVO,orderItem);
            orderItem.setUnitPrice(cartVO.getProductPrice());
            Integer totalPrice = orderItem.getUnitPrice() * orderItem.getQuantity();
            orderItem.setTotalPrice(totalPrice);
            orderItem.setOrderNo(orderNo);
            orderItemList.add(orderItem);
        }
        return orderItemList;
    }

    /**
     * 扣库存
     */
    private void cutStock(List<CartVO> cartVOList) {
        for (CartVO cartVO : cartVOList) {
            Product product = productMapper.selectByPrimaryKey(cartVO.getProductId());
            int stock = product.getStock() - cartVO.getQuantity();
            product.setStock(stock);
            productMapper.updateByPrimaryKeySelective(product);
        }
    }

    /**
     * 验证购物车的商品是否在售状态，库存够不够
     * @param cartVOList
     */
    private void validStatusAndStock(List<CartVO> cartVOList) {
        for (CartVO cartVO:cartVOList){
            Product product = productMapper.selectByPrimaryKey(cartVO.getProductId());
            if (product == null || product.getStatus().equals(Constant.ProductStatus.NOT_SELL)) {
                throw new MallException(MallExceptionEnum.NOT_SELL);
            }
            if (product.getStock()<cartVO.getQuantity()) {
                throw new MallException(MallExceptionEnum.NOT_ENOUGH);
            }
        }
    }
}
