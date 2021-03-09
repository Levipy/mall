package com.levi.mall.exception;

/**
 * 异常枚举类
 *
 * @author Levi
 */
public enum MallExceptionEnum {
    //用户名不能为空
    NEED_USER_NAME(10001, "用户名不能为空"),
    //密码不能为空
    NEED_PASSWORD(10002, "密码不能为空"),
    //密码不能少于8位
    NEED_PASSWORD_LENGTH_8(10003, "密码不能少于8位"),
    //用户名已存在
    NAME_EXISTED(10004, "用户名已存在"),
    //插入失败
    INSERT_FAIL(10005, "插入失败"),
    //密码错误
    PASSWORD_ERROR(10006, "密码错误"),
    //需要登录
    NEED_LOGIN(10007, "需要登录"),
    //更新失败
    UPDATE_FAILED(10008, "更新失败"),
    //你不是管理员
    NOT_ADMIN(10009, "你不是管理员"),
    //需要参数
    PARAM_NOT_NULL(10010, "需要参数"),
    //插入失败
    INSERT_FAILED(10011, "插入失败"),
    //请求参数错误
    REQUEST_ARGUMENT_ERROR(10012, "请求参数错误"),
    //删除失败
    DELETE_FAILED(10013, "删除失败"),
    //新建文件夹失败
    MKDIR_FAILED(10014, "新建文件夹失败"),
    UPLOAD_FAILED(10015,"上传文件失败"),
    NOT_SELL(10016, "商品状态异常，无法添加到购物车"),
    NOT_ENOUGH(10017, "商品库存不足"),
    CART_IS_NULL(10018, "你的购物车为空"),
    NO_ENUM(10019, "未找到对应的枚举类"),
    ANOTHER_ERROR(10020, "不可控因素"),




    //系统异常
    SYSTEM_ERROR(20000, "SYSTEM_ERROR");


    Integer code;
    String msg;

    MallExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
