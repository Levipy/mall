package com.levi.mall.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;
@ApiModel
@Data
public class User {
    private Integer id;

    private String username;

    private String password;

    private String personalizedSignature;

    private Integer role;

    private Date createTime;

    private Date updateTime;

}