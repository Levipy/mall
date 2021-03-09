package com.levi.mall.request;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 *
 * @auther Levi
 * @Date: 2021/03/07/20:34
 * @Description: 修改商品信息的传输实体类
 */
@Data
public class UpdateProductReq {
    @NotNull(message = "商品id不能为空")
    private Integer id;

    private String name;

    private String image;

    private String detail;

    private Integer categoryId;

    @Min(value = 1,message = "价格不能小于1分")
    private Integer price;

    @Max(value = 1000,message = "商品库存不能超过10000")
    private Integer stock;

    private Integer status;
}
