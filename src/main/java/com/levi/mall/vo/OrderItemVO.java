package com.levi.mall.vo;

import com.levi.mall.entity.OrderItem;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @auther Levi
 * @Date: 2021/03/10/9:06
 * @Description:
 */
@Data
public class OrderItemVO {

    private String orderNo;

    private String productName;

    private String productImg;

    private Integer unitPrice;

    private Integer quantity;

    private Integer totalPrice;

}
