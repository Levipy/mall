package com.levi.mall.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

/**
 * 购物车VO，展示给前端
 * @author Levi
 */
@ApiModel
@Data
public class CartVO {
    private Integer id;

    private Integer productId;

    private Integer userId;

    private Integer quantity;

    private Integer selected;

    private String productName;

    private String productImg;

    private Integer productPrice;

    private Integer totalPrice;


}