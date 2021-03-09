package com.levi.mall.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Levi
 * description: 前台展示商品列表的
 */
@ApiModel
@Data
public class ListForCustomerProductReq {

    private String keyword;

    private String orderBy;

    private Integer categoryId;

    private Integer pageNum = 1;

    private Integer pageSize = 5;



}