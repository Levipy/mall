package com.levi.mall.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;
@ApiModel
@Data
public class OrderItem {
    private Integer id;

    private String orderNo;

    private Integer productId;

    private String productName;

    private String productImg;

    private Integer unitPrice;

    private Integer quantity;

    private Integer totalPrice;

    private Date createTime;

    private Date updateTime;

}