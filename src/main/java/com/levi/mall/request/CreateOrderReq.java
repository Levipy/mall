package com.levi.mall.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 *
 * @auther Levi
 * @Date: 2021/03/09/16:21
 * @Description:
 */
@Data
public class CreateOrderReq {
    @NotNull(message = "收货人姓名不能为空")
    private String receiverName;
    @NotNull(message = "手机号码不能为空")
    private String receiverMobile;
    @NotNull(message = "收货地址不能为空")
    private String receiverAddress;

    private Integer postage = 0;

    private Integer paymentType = 1;
}
