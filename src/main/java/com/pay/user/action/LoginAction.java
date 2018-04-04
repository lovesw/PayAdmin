package com.pay.user.action;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.jfinal.aop.Before;
import com.jfinal.aop.Duang;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.plugin.ehcache.CacheKit;
import com.pay.data.controller.BaseController;
import com.pay.data.utils.FileImageUtils;
import com.pay.data.utils.FieldUtils;
import com.pay.data.utils.JwtUtil;
import com.pay.data.validator.LoginValidator;
import com.pay.user.model.Menu;
import com.pay.user.model.Permission;
import com.pay.user.model.User;
import com.pay.user.service.LoginService;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
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
            getResponse().setHeader(FieldUtils.AUTHORIZATION, token);
            Map<String, Object> map = new HashMap<>(2);
            map.put("source", list);
            map.put("menu", menuList);
            super.success(map);
        }
    }

    /***
     *
     * @param pdfName pdf 名称
     * @param token 校验的token
     */
    public void pdf(String id, String pdfName, String token) {
        //校验token
        if (StrUtil.equals(CacheKit.get("pdfToken", id), token)) {
            getResponse().setCharacterEncoding("UTF-8");
            getResponse().setHeader("Content-Disposition", "inline;filename=" + pdfName);
            getResponse().setContentType("application/pdf");
            try {
                byte[] inputStream = FileUtil.readBytes(FileImageUtils.readPDF(pdfName));
                OutputStream stream = getResponse().getOutputStream();
                stream.write(inputStream);
                stream.flush();
                stream.close();
                renderNull();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            render("/404.html");
        }

    }

    /**
     * 档案附下载
     *
     * @param token   token
     * @param id      Id
     * @param pdfName 档案的附件名称
     */
    public void download(String token, int id, String pdfName) {
        if (StrUtil.equals(CacheKit.get("ArchToken", id), token)) {
            renderFile(FileImageUtils.readPDF(pdfName));
        } else {
            render("/404.html");
        }

    }

}
