package com.pay.user.action;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.jfinal.aop.Before;
import com.jfinal.aop.Duang;
import com.jfinal.core.paragetter.Para;
import com.pay.data.controller.BaseController;
import com.pay.data.entity.ThemeEntity;
import com.pay.data.interceptors.Get;
import com.pay.data.interceptors.PermissionInterceptor;
import com.pay.data.interceptors.Post;
import com.pay.data.interceptors.Put;
import com.pay.data.utils.CommonUtils;
import com.pay.user.model.Theme;
import com.pay.user.service.ThemeService;

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
    public void add(String serviceType, Long cooperationId, Long companyId, String str) {
        String makeId = getUserId();

        //将传入的数组转为jsonArray
        JSONArray jsonArray = JSONUtil.parseArray(str);
        //将jsonArray转为MakeDesign集合
        List<ThemeEntity> list = JSONUtil.toList(jsonArray, ThemeEntity.class);

        boolean bool = themeService.addService(serviceType, cooperationId, companyId, makeId, list);
        result(bool);
    }


    /**
     * 用户查看主题列表
     */
    @Before(Get.class)
    public void list(String date) {
        if (StrUtil.isBlank(date)) {
            error("查询参数不正确");
        } else {
            String userId = getUserId();
            success(themeService.listService(CommonUtils.getOneMonth(date), userId));
        }
    }

    /**
     * 查询指定的月份的所有相关的主题信息
     */
    @Before(Get.class)
    public void allList(String date) {
        if (StrUtil.isBlank(date)) {
            error("查询参数不正确");
        } else {
            String userId = getUserId();
            success(themeService.allListService(CommonUtils.getOneMonth(date), userId));
        }
    }

    /**
     * 上传者修改用户信息
     */
    @Before(Put.class)
    public void update(@Para("") Theme theme) {
        boolean bool = themeService.updateService(theme);
        result(bool);
    }

    /**
     * 点击查看为未通过主题的原因
     *
     * @param tid 主题的Id
     */
    @Before(Get.class)
    public void lookCausation(int tid) {
        success(themeService.lookCausationService(tid));
    }
}
