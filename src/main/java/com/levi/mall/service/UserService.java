package com.levi.mall.service;

import com.levi.mall.entity.User;
import com.levi.mall.exception.MallException;

import java.security.NoSuchAlgorithmException;

public interface UserService {
    /**
     * 测试用
     * @return
     */
    User getUser();
    /**
     * 用户注册
     * @param username
     * @param password
     * @throws MallException
     */
    void regist(String username, String password) throws MallException;

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     * @throws MallException
     * @throws NoSuchAlgorithmException
     */
    User login(String username, String password) throws NoSuchAlgorithmException, MallException;

    /**
     * 用户修改个性签名
     * @param user
     * @throws MallException
     */
    void updateSignature(User user) throws MallException;

    /**
     * 校验是否是管理员
     * role为2，说明是管理员，否则为普通用户
     * @param user
     * @return
     */
    Boolean checkAdminRole(User user);
}
