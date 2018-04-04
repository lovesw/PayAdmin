package com.pay.user.action;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.jfinal.aop.Before;
import com.jfinal.aop.Duang;
import com.pay.data.controller.BaseController;
import com.pay.data.entity.ThemeEntity;
import com.pay.data.interceptors.PermissionInterceptor;
import com.pay.data.interceptors.Post;
import com.pay.user.model.Theme;
import com.pay.user.service.ThemeService;

import java.util.Date;
import java.util.List;

/**
 * @createTime: 2018/4/3
 * @author: HingLo
 * @description: 用户主题上传管理, 需要权限拦截
 */
@Before(PermissionInterceptor.class)
public class ThemeAction extends BaseController {

    /**
     * 服务层处理对象，在添加的时候需要事务
     */
    private static final ThemeService themeService = Duang.duang(ThemeService.class);

    /**
     * 批量添加主题信息
     *
     * @param serviceType   业务类型，默认是：手机主题
     * @param cooperationId 合作市场商家的Id
     * @param str           批量的主题信息
     */
    @Before(Post.class)
    public void add(String serviceType, Long cooperationId, String str) {
        String makeId = getUserId();

        //将传入的数组转为jsonArray
        JSONArray jsonArray = JSONUtil.parseArray(str);
        //将jsonArray转为MakeDesign集合
        List<ThemeEntity> list = JSONUtil.toList(jsonArray, ThemeEntity.class);

        boolean bool = themeService.addService(serviceType, cooperationId, makeId, list);
        result(bool);
    }

    /**
     * 按商家列表
     */
    public void clist() {
        success(themeService.cListService());
    }

    /**
     * 获取用户列表，就是在用户填写选择设计人的时候的接口
     */
    public void ulist() {
        success(themeService.uListService());
    }

    /**
     * 用户查看主题列表
     */
    public void list(String date) {
        if (StrUtil.isBlank(date)) {
            error("查询参数不正确");
        } else {
            String userId = getUserId();
            Date date1 = DateUtil.parseDate(date + "-01");
            success(themeService.listService(date1, userId));
        }
    }

    /**
     * 查询指定的月份的所有相关的主题信息
     */
    public void allList(String date) {
        if (StrUtil.isBlank(date)) {
            error("查询参数不正确");
        } else {
            String userId = getUserId();
            Date date1 = DateUtil.parseDate(date + "-01");
            success(themeService.allListService(date1, userId));
        }
    }


    /**
     * 上传者修改用户信息
     */
    public void update() {
        Theme theme = getBean(Theme.class, "");
        boolean bool = themeService.updateService(theme);
        result(bool);
    }

}
