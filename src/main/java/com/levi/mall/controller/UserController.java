package com.levi.mall.controller;

import com.levi.mall.common.ApiRestResponse;
import com.levi.mall.common.Constant;
import com.levi.mall.entity.User;
import com.levi.mall.exception.MallException;
import com.levi.mall.exception.MallExceptionEnum;
import com.levi.mall.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @ApiOperation("测试项目打通")
    @GetMapping("/test")
    public User personalPage() {
        return userService.getUser();
    }

    /**
     * 用户注册接口
     * @param userName
     * @param password
     * @return
     * @throws MallException
     */
    @ApiOperation("用户注册")
    @PostMapping("/register")
    public ApiRestResponse regist(@RequestParam String userName, @RequestParam String password) throws MallException {
        if (StringUtils.isEmpty(userName)) {
            return ApiRestResponse.error( MallExceptionEnum.NEED_USER_NAME);
        }
        if (StringUtils.isEmpty(password)) {
            return ApiRestResponse.error(MallExceptionEnum.NEED_PASSWORD);
        }
        if (password.length()<8) {
            return ApiRestResponse.error(MallExceptionEnum.NEED_PASSWORD_LENGTH_8);
        }
        userService.regist(userName,password);
        return ApiRestResponse.success();
    }
    @ApiOperation("用户登录")
    @PostMapping("/login")
    public ApiRestResponse longin(@RequestParam String userName, @RequestParam String password,
                                  HttpSession session) throws NoSuchAlgorithmException, MallException {
        if (StringUtils.isEmpty(userName)) {
            return ApiRestResponse.error( MallExceptionEnum.NEED_USER_NAME);
        }
        if (StringUtils.isEmpty(password)) {
            return ApiRestResponse.error(MallExceptionEnum.NEED_PASSWORD);
        }
        User user = userService.login(userName, password);
        user.setPassword("********");
        session.setAttribute(Constant.MALL_USER, user);
        return  ApiRestResponse.success(user);
    }
    @ApiOperation("用户修改个签")
    @PostMapping("/user/update")
    public ApiRestResponse updateSignature(HttpSession session, String signature) throws MallException {
        User current_user = (User) session.getAttribute(Constant.MALL_USER);
        System.out.println(current_user);
        if (current_user == null) {
            return ApiRestResponse.error(MallExceptionEnum.NEED_LOGIN);
        }

        User user = new User();
        user.setId(current_user.getId());
        user.setPersonalizedSignature(signature);
        userService.updateSignature(user);
        return ApiRestResponse.success();
    }

    /**
     * 登出接口
     * @param session
     * @return
     */
    @ApiOperation("用户登出")
    @PostMapping("/user/logout")
    public ApiRestResponse userLogout(HttpSession session) {
        session.removeAttribute(Constant.MALL_USER);
        return ApiRestResponse.success();
    }

    /**
     * 管理员登录接口
     * @param userName
     * @param password
     * @param session
     * @return
     * @throws MallException
     *  .,m;l,;l;l
     * @throws NoSuchAlgorithmException
     */
    @ApiOperation("管理员登录")
    @PostMapping("/adminLogin")
    public ApiRestResponse checkedAdminLogin(@RequestParam String userName, @RequestParam String password
            ,HttpSession session) throws MallException{
        if (StringUtils.isEmpty(userName)) {
            return ApiRestResponse.error( MallExceptionEnum.NEED_USER_NAME);
        }
        if (StringUtils.isEmpty(password)) {
            return ApiRestResponse.error(MallExceptionEnum.NEED_PASSWORD);
        }
        User user = userService.login(userName, password);

        if (userService.checkAdminRole(user)) {
            //true说明是管理员
            user.setPassword("********");
            session.setAttribute(Constant.MALL_USER,user);
            return ApiRestResponse.success(user);
        }else {
            return ApiRestResponse.error(MallExceptionEnum.NOT_ADMIN);
        }

    }
}
