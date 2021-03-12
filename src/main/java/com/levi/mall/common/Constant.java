package com.levi.mall.common;

import com.google.common.collect.Sets;
import com.levi.mall.exception.MallException;
import com.levi.mall.exception.MallExceptionEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

/**
 * @author Levi
 */
@Component
public class Constant {

    public static final String SALT = "fdsagsg1G*/-=.你好~%$#@^&*(";
    public static final String MALL_USER = "mall_user";

    public static  String FILE_UPLOAD_DIR ;

    @Value("${file.upload.dir}")
    public void setFileUploadDir(String uploadDir) {
        FILE_UPLOAD_DIR = uploadDir;
    }


    public static URI getHost(URI uri) {
        URI effectiveUri;
        try {
            effectiveUri = new URI(uri.getScheme(), uri.getUserInfo(),uri.getHost(), uri.getPort(),  null,null,null);
        } catch (URISyntaxException e) {
            effectiveUri = null;
        }
        return effectiveUri;
    }

    public interface listForCustomerOrderBy {
        Set<String> PRICE_DESC_ASC = Sets.newHashSet("price desc", "price acs");
    }

    public interface ProductStatus{
        Integer NOT_SELL = 0;
        Integer SELL = 1;
    }

    public interface CartSelected{
        Integer NOT_SELECTED = 0;
        Integer SELECTED = 1;
    }

    /**
     * 订单状态枚举类
     */
    public enum OrderStatusEnum {
        /**
         * 取消订单
         */
        CANCEL_ORDER(0, "取消订单"),
        /**
         * 已下单未付款
         */
        NOT_PAID(10, "已下单未付款"),
        /**
         *已付款
         */
        PAID(20, "已付款"),
        /**
         *已发货
         */
        DEVELIVERED(30, "已发货"),
        /**
         *已签收
         */
        FINASHED(40, "已签收");

        private int code;
        private String message;

        public static OrderStatusEnum getOrderStatusEnum(Integer code) {
            for (OrderStatusEnum orderStatusEnum : values()) {
                if (orderStatusEnum.getCode() == code) {
                    return orderStatusEnum;
                }
            }
            throw new MallException(MallExceptionEnum.NO_ENUM);
        }

        OrderStatusEnum(Integer code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    /**
     * 支付方式枚举类
     */
    public enum PayType{
        /**
         * 支付宝支付
         */
        ALI_PAY(1, "支付宝支付"),
        /**
         *微信支付
         */
        WECHAT_PAY(2, "微信支付"),
        /**
         *银联支付
         */
        YINLAIN_PAY(3, "银联支付");


        private int pay;
        private String msg;

        PayType(int pay, String msg) {
            this.pay = pay;
            this.msg = msg;
        }

        public int getPay() {
            return pay;
        }

        public void setPay(int pay) {
            this.pay = pay;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

}
