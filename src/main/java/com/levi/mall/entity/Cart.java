package com.levi.mall.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;
@ApiModel
@Data
public class Cart {
    private Integer id;

    private Integer productId;

    private Integer userId;

    private Integer quantity;

    private Integer selected;

    private Date createTime;

    private Date updateTime;

}