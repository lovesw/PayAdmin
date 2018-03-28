package com.pay.user.action;


import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.POST;
import com.pay.data.controller.BaseController;
import com.pay.data.interceptors.Get;
import com.pay.data.utils.FiledUtils;
import com.pay.data.utils.JwtUtil;
import com.pay.data.validator.LoginValidator;
import com.pay.user.model.Menu;
import com.pay.user.model.Permission;
import com.pay.user.model.User;
import com.pay.user.service.LoginService;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @createTime: 2018/1/2
 * @author: HingLo
 * @description: 登录Action
 */
@Slf4j
public class LoginAction extends BaseController {
    private final LoginService loginService = new LoginService();

    /**
     * 系统管理员后台登录
     */
    @Before({POST.class, LoginValidator.class})
    public void login(String username, String password) {
        //调用服务层的方法进行校验
        User user = loginService.loginMethodService(username, password);
        if (user == null) {
            error("账号或者密码错误");
        } else {
            //查找全部的权限，然后进行返回给用户
            List<Permission> list = loginService.findPermissionService(username);
            //查找菜单
            List<Menu> menuList = loginService.findMenuService(username);
            String token = JwtUtil.createJWT(user.getId(), user.toString());
            getResponse().setHeader(FiledUtils.AUTHORIZATION, token);
            Map<String, Object> map = new HashMap<>(2);
            map.put("source", list);
            map.put("menu", menuList);
            super.success(map);
        }
    }

}
