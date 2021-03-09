package com.levi.mall.common;

import com.levi.mall.exception.MallExceptionEnum;
import io.swagger.annotations.ApiModel;

/**
 * 通用返回对象，根据请求成功或者失败返回不同的Response对象
 * status 状态码
 * msg 响应的信息
 * data 带的数据
 */
@ApiModel
public class ApiRestResponse<T> {
    private Integer status;
    private String msg;
    private T data;
    private static final int OK_CODE = 10000;
    private static final String OK_MSG = "SUCCESS";

    public ApiRestResponse(Integer status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public ApiRestResponse(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public ApiRestResponse() {
        this(OK_CODE, OK_MSG);
    }

    /**
     * 成功了返回10000和success
     * @param
     * @param <T>
     * @return
     */
    public static <T> ApiRestResponse<T> success(){
        return new ApiRestResponse<>();
    }

    /**
     * 请求成功了返回带data的response对象
     * @param result
     * @param <T>
     * @return
     */
    public static <T> ApiRestResponse<T> success(T result){
        ApiRestResponse<T> restResponse = new ApiRestResponse<>();
        restResponse.setData(result);
        return restResponse;
    }

    /**
     * 请求失败了调用error方法
     * @param code
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> ApiRestResponse<T> error(Integer code, String msg) {
        return new ApiRestResponse<>(code, msg);
    }

    /**
     * 使用异常枚举的error方法
     * @param e
     * @param <T>
     * @return
     */
    public static <T> ApiRestResponse<T> error(MallExceptionEnum e) {
        return new ApiRestResponse<>(e.getCode(),e.getMsg());
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static int getOkCode() {
        return OK_CODE;
    }

    public static String getOkMsg() {
        return OK_MSG;
    }
}
