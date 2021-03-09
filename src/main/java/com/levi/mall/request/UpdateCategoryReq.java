package com.levi.mall.request;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: levi
 * @Date: 2021/03/06/14:02
 * @Description:
 */
@Data
public class UpdateCategoryReq {
    @NotNull(message = "id不能为null")
    private Integer id;

    @Size(message = "name的值应该在2-5之间",min = 2,max = 5)
    private String name;

    @Max(3)
    private Integer type;

    private Integer parentId;

    private Integer orderNum;

}
