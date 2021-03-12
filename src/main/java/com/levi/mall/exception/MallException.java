package com.levi.mall.exception;

/**
 * 自定义异常
 * @author Levi
 */
public class MallException extends RuntimeException{
    private final Integer code;
    private final String message;

    public MallException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public MallException(MallExceptionEnum exceptionEnum) {

        this(exceptionEnum.getCode(), exceptionEnum.getMsg());
    }

    public Integer getCode() {
        return code;
    }

    /**
     * Returns the detail message string of this throwable.
     *
     * @return the detail message string of this {@code Throwable} instance
     * (which may be {@code null}).
     */
    @Override
    public String getMessage() {
        return message;
    }
}
