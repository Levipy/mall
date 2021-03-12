package com.levi.mall.vo;

import com.levi.mall.entity.OrderItem;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @auther Levi
 * @Date: 2021/03/10/10:22
 * @Description:
 */
@Data
public class OrderVO {

    private String orderNo;

    private Integer userId;

    private Integer totalPrice;

    private String receiverName;

    private String receiverMobile;

    private String receiverAddress;

    private Integer orderStatus;

    private Integer postage;

    private Integer paymentType;

    private Date deliveryTime;

    private Date payTime;

    private Date endTime;

    private List<OrderItemVO> orderItemVOList;

    private String orderStatusName;
}
