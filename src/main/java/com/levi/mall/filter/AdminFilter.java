package com.levi.mall.filter;

import com.levi.mall.common.ApiRestResponse;
import com.levi.mall.common.Constant;
import com.levi.mall.entity.User;
import com.levi.mall.exception.MallExceptionEnum;
import com.levi.mall.service.CategoryService;
import com.levi.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: levi
 * @Date: 2021/03/06/15:00
 * @Description: 统一校验是否登录而且是不是管理员
 */
public class AdminFilter implements Filter {

    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        User current_user = (User) session.getAttribute(Constant.MALL_USER);

        //判断是否登录，没有登陆就提示NEED_LOGIN，然后return中断程序
        if (current_user == null) {
            PrintWriter out = new HttpServletResponseWrapper((HttpServletResponse) servletResponse).getWriter();
            out.write("{\n" +
                    "    \"status\": 10007,\n" +
                    "    \"msg\": \"NEED_LOGIN\",\n" +
                    "    \"data\": null\n" +
                    "}");
            out.flush();
            out.close();
            return;
        }
        Boolean adminrole = userService.checkAdminRole(current_user);
        //校验是否是管理员,是管理员的话就放行
        if (adminrole) {
           filterChain.doFilter(servletRequest,servletResponse);
        }else{
            //不是管理员就提示NOT_ADMIN
            PrintWriter out = new HttpServletResponseWrapper((HttpServletResponse) servletResponse).getWriter();
            out.write("{\n" +
                    "    \"status\": 10009,\n" +
                    "    \"msg\": \"NOT_ADMIN\",\n" +
                    "    \"data\": null\n" +
                    "}");
            out.flush();
            out.close();
        }

    }

    @Override
    public void destroy() {

    }
}
