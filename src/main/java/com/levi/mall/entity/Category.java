package com.levi.mall.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;
@ApiModel
@Data
public class Category {
    private Integer id;

    private String name;

    private Integer type;

    private Integer parentId;

    private Integer orderNum;

    private Date createTime;

    private Date updateTime;

}