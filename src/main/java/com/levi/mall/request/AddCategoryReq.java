package com.levi.mall.request;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: levi
 * @Date: 2021/03/05/17:37
 * @Description:  请求传输类
 */
@Data
public class AddCategoryReq {

    @NotNull(message = "name不能为空")
    @Size(message = "name的值应该在2-5之间",min = 2,max = 5)
    private String name;

    @NotNull(message = "type不能为空")
    @Max(3)
    private Integer type;

    @NotNull(message = "parentId不能为空")
    private Integer parentId;

    @NotNull(message = "orderNum不能为空")
    private Integer orderNum;

}
