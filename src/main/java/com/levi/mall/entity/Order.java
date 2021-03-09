package com.levi.mall.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;
@ApiModel
@Data
public class Order {
    private Integer id;

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

    private Date createTime;

    private Date updateTime;

}