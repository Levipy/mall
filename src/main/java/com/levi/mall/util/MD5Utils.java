package com.levi.mall.util;

import com.levi.mall.common.Constant;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.util.DigestUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

    public static String getMD5Str(String str) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        return Base64.encodeBase64String(md5.digest((str+Constant.SALT).getBytes()));
//        return DigestUtils.md5DigestAsHex(str.getBytes());
    }

    /**
     * 另一种加盐处理方法
      * @param args
     * @throws NoSuchAlgorithmException
     */
//    public static String getMD5Str1(String str) throws NoSuchAlgorithmException {
//        char[] ca = str.toCharArray();
//        for (int i = 0; i < ca.length; i++) {
//            ca[i] = ca[i] + (char) Constant.SALT;
//        }
//        String new_ca = new String(ca);
//        return DigestUtils.md5DigestAsHex(new_ca.getBytes());
//    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.out.println(getMD5Str("123456789"));
    }
}
