package com.levi.mall.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
/**
 * @author Levi
 */
@ApiModel
@Data
public class Product implements Serializable{
    private Integer id;

    private String name;

    private String image;

    private String detail;

    private Integer categoryId;

    private Integer price;

    private Integer stock;

    private Integer status;

    private Date createTime;

    private Date updateTime;


}