package com.levi.mall.service.impl;

import com.levi.mall.common.Constant;
import com.levi.mall.dao.UserMapper;
import com.levi.mall.entity.User;
import com.levi.mall.exception.MallException;
import com.levi.mall.exception.MallExceptionEnum;
import com.levi.mall.service.UserService;
import com.levi.mall.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public User getUser() {
        User user = userMapper.selectByPrimaryKey(1);
        return user;
    }

    /**
     * 用户注册
     * @param username
     * @param password
     * @throws MallException
     */
    @Override
    public void regist(String username, String password) throws MallException {
        //先查询用户有没有被注册
        User result = userMapper.selectByName(username);
        if (result != null) {
            throw new MallException(MallExceptionEnum.NAME_EXISTED);
        }
        User user = new User();
        user.setUsername(username);
        try {
            user.setPassword(MD5Utils.getMD5Str(password));
            System.out.println("");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        int count = userMapper.insertSelective(user);
        if (count == 0) {
            throw new MallException(MallExceptionEnum.INSERT_FAIL);
        }
    }

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     * @throws MallException
     * @throws NoSuchAlgorithmException
     */
    @Override
    public User login(String username, String password) throws MallException, NoSuchAlgorithmException {
        String md5Password = MD5Utils.getMD5Str(password);
        User user = userMapper.selectLogin(username, md5Password);
        if (user == null) {
            throw new MallException(MallExceptionEnum.PASSWORD_ERROR);
        }
        user.setPassword("********");
        return user;
    }

    /**
     * 用户修改个性签名
     * @param user
     * @throws MallException
     */
    @Override
    public void updateSignature(User user) throws MallException {
        int num = userMapper.updateByPrimaryKeySelective(user);
        if (num > 1) {
            throw new MallException(MallExceptionEnum.UPDATE_FAILED);
        }
    }

    /**
     * 校验是否是管理员
     * role为2，说明是管理员，否则为普通用户
     * @param user
     * @return
     */
    @Override
    public Boolean checkAdminRole(User user) {
        if (user.getRole().equals(2)) {
            return true;
        } else {
            return false;
        }
    }
}
