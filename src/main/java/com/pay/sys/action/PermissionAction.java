package com.pay.sys.action;

import cn.hutool.core.util.StrUtil;
import com.jfinal.aop.Before;
import com.jfinal.aop.Duang;
import com.jfinal.core.paragetter.Para;
import com.pay.data.controller.BaseController;
import com.pay.data.interceptors.Delete;
import com.pay.data.interceptors.Get;
import com.pay.data.interceptors.Post;
import com.pay.data.interceptors.Put;
import com.pay.data.utils.FieldUtils;
import com.pay.sys.service.PermissionService;
import com.pay.user.model.Menu;
import com.pay.user.model.Permission;
import com.pay.user.model.Role;
import com.pay.user.service.LoginService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @createTime: 2018/3/1
 * @author: HingLo
 * @description: 权限管理，主要是对权限，角色，进行管理
 */
public class PermissionAction extends BaseController {

    private final PermissionService permissionService = Duang.duang(PermissionService.class);


    private final LoginService loginService = new LoginService();
    //****************************************** 角色管理**************************************************/

    /**
     * 角色列表
     */
    @Before(Get.class)
    public void listRole() {
        List<Role> list = permissionService.listRoleListService();
        success(list);
    }

    /***
     * 添加角色
     * @param role 角色对象
     */
    @Before(Post.class)
    public void addRole(@Para("") Role role) {
        boolean bool = permissionService.addRoleService(role);
        result(bool);
    }

    /**
     * 删除角色
     *
     * @param id 角色的唯一Id
     */
    @Before(Delete.class)
    public void deleteRole(String id) {
        boolean bool = permissionService.deleteRoleService(id);
        result(bool);

    }

    /**
     * 用于将角色标记为指定的类型
     *
     * @param type 标记的类型
     */
    @Before(Put.class)
    public void mark(int type, Long id) {
        if (type == 1) {
            //设计师标记
            FieldUtils.DESIGN = id;
            success("标记身份成功");
        } else {
            error("标记身份失败");
        }


    }

    //****************************************** 授权管理**************************************************/

    /**
     * 查看角色的权限
     *
     * @param id 角色的唯一Id
     */
    @Before(Get.class)
    public void listRolePermission(String id) {
//        指定角色的Id
        List<Permission> permissionList = permissionService.listRolePermissionService(id);
        success(permissionList);
    }

    /**
     * 给指定的角色添加授权
     *
     * @param roleId 角色的唯一Id
     */
    @Before(Post.class)
    public void addRolePermission(Long roleId) {

        //1：获取菜单的id
        Long[] menuId = getParaValuesToLong("menuId[]");
        //2:获取权限的所有Id
        Long[] permissionId = getParaValuesToLong("permissionId[]");
        if (StrUtil.isBlank(roleId.toString()) || menuId == null || menuId.length < 1 || permissionId == null || permissionId.length < 1) {
            result(false, "授权参数不正确");
        }
        boolean bool;
        try {
            //事务异常捕获
            bool = permissionService.addRolePermissionService(menuId, permissionId, roleId);
        } catch (Exception e) {
            e.printStackTrace();
            bool = false;
        }
        result(bool);

    }

    /**
     * 刷新权限
     */
    @Before(Get.class)
    public void refreshPermission() {
        String userId = getUserId();
        List<Permission> list = loginService.findPermissionService(userId);
        //查找菜单
        List<Menu> menuList = loginService.findMenuService(userId);
        Map<String, Object> map = new HashMap<>(2);
        map.put("source", list);
        map.put("menu", menuList);
        success(map);


    }


    //****************************************** 权限管理**************************************************/

    /**
     * 浏览全部权限列表
     */
    @Before(Get.class)
    public void listPermission() {
        success(permissionService.listPermissionService());
    }

    /**
     * 添加权限
     */
    @Before(Post.class)
    public void addPermission(@Para("") Permission permission) {
        boolean bool = permissionService.addPermissionService(permission);
        result(bool, "添加失败");
    }


    /**
     * 删除指定的权限
     *
     * @param id 权限的唯一ID
     */
    @Before(Delete.class)
    public void deletePermission(String id) {
        boolean bool = permissionService.deletePermissionService(id);
        result(bool);

    }

    /**
     * 修改指定的权限
     *
     * @param permission 权限的对象
     */
    @Before(Put.class)
    public void updatePermission(@Para("") Permission permission) {
        boolean bool = permissionService.changePermissionService(permission);
        result(bool);

    }
    //****************************************** 用户角色授权管理**************************************************/

    /**
     * 查询全部用户的角色
     */
    @Before(Get.class)
    public void listUserRole() {
        success(permissionService.listUserRoleService());
    }

    /**
     * 给用户授权指定的角色
     *
     * @param userId 用户的Id
     */
    @Before(Post.class)
    public void addUserRole(String userId) {
        Long[] roleId = getParaValuesToLong("roleId[]");
        boolean bool;
        try {
            //服务层涉及到事务，需要进行事务回滚异常捕获
            bool = permissionService.addUserRoleService(userId, roleId);
        } catch (RuntimeException e) {
            bool = false;
        }
        result(bool, "授予角色信息失败");
    }

    //****************************************** 菜单管理**************************************************/

    /**
     * 菜单列表
     */
    @Before(Get.class)
    public void listMenu() {
        List<Menu> list = permissionService.listMenuService();
        success(list);
    }

    /**
     * 添加菜单
     *
     * @param menu 菜单对象
     */
    @Before(Post.class)
    public void addMenu(@Para("") Menu menu) {
        boolean bool = permissionService.addMenuService(menu);
        result(bool);
    }

    /**
     * 删除菜单
     *
     * @param id 菜单的唯一Id
     */
    public void deleteMenu(String id) {
        boolean bool = permissionService.deleteMenuService(id);
        result(bool);

    }

    /**
     * 更新菜单信息
     *
     * @param menu 菜单对象
     */
    public void updateMenu(@Para("") Menu menu) {
        result(permissionService.updateMenuService(menu));
    }

}
