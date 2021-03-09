package com.levi.mall.exception;

/**
 * 自定义异常
 */
public class MallException extends RuntimeException{
    private final Integer code;
    private final String message;

    public MallException(Integer code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public MallException(MallExceptionEnum exceptionEnum) {
        this(exceptionEnum.getCode(), exceptionEnum.getMsg());
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return message;
    }
}
