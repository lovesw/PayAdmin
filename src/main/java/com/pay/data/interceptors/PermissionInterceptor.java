package com.pay.data.interceptors;

import cn.hutool.core.util.StrUtil;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.plugin.ehcache.CacheKit;
import com.pay.data.entity.Result;
import com.pay.data.enums.ExceptionEnum;
import com.pay.data.utils.ResultUtils;
import com.pay.user.model.Permission;

import java.util.ArrayList;
import java.util.List;

/**
 * @createTime: 2018/3/5
 * @author: HingLo
 * @description: 权限管理拦截器
 */
public class PermissionInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation inv) {
        Controller controller = inv.getController();
        String userId = (String) controller.getRequest().getAttribute("userId");
        String url = controller.getRequest().getRequestURI();
        if (StrUtil.isBlank(userId)) {
            Result result = ResultUtils.error("登录用户失效");
            controller.renderJson(result);
        } else {
            //查询该用户请求该权限是否有效
            List<String> list = findPermission(userId);
            //判断是否包含权限url
            if (list.contains(url)) {
                inv.invoke();
                return;
            }
        }
        Result result = ResultUtils.error(ExceptionEnum.NOTPERMISSION);
        controller.renderJson(result);
    }

    /**
     * 权限验证，权限验证添加了查询缓存
     *
     * @param userId 当前用户
     * @return 权限列表
     */
    private List<String> findPermission(String userId) {
        String sql = "select * from permission where id in (select permission_id from role_permission where role_id in (select role_id from user_role where user_id=?) )";
        //从缓存中获取权限
        List<String> list = CacheKit.get("Permission", userId);
        if (list == null) {
            list = new ArrayList<>();
            //查询全部权限
            List<Permission> permissionList = Permission.dao.find(sql, userId);
            //遍历权限URL,并添加到List中
            for (Permission permission : permissionList) {
                list.add(permission.getUrl());
            }
            //缓存权限URL
            CacheKit.put("Permission", userId, list);
        }
        return list;
    }

}
