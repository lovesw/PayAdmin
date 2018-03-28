package com.pay.sys.service;

import cn.hutool.core.util.StrUtil;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.pay.user.model.*;

import java.util.*;

/**
 * @createTime: 2018/3/1
 * @author: HingLo
 * @description: 权限管理的服务层
 */
public class PermissionService {
    /**
     * 权限列表
     *
     * @return 权限集合
     */
    public List<?> listPermissionService() {
        String sql = "select p.id,m.id as mid,m.name as mname,p.name,p.url,p.content from permission  as p, menu as m where p.mid=m.id order by p.id asc";
        return Db.find(sql);
    }

    /**
     * 添加权限
     *
     * @param permission 权限对象信息
     * @return 操作结果
     */
    public boolean addPermissionService(Permission permission) {
        String url = permission.getUrl();
        //操作去掉url中的空格
        permission.setUrl(url.trim());
        permission.setDate(new Date());
        return permission.save();

    }

    /**
     * 删除权限
     *
     * @param id 权限的唯一ID
     * @return 操作结果
     */
    public boolean deletePermissionService(String id) {
        return StrUtil.isNotBlank(id) && Permission.dao.deleteById(id);
    }

    /**
     * 更新权限信息
     *
     * @param permission 包含了权限ID的权限对象集合
     * @return 更新结果
     */
    public boolean changePermissionService(Permission permission) {
        return permission.update();
    }

    /***
     * 添加用户的角色
     * @param userId 用户的Id
     * @param roleId roleId 集合
     * @return 授权结果
     */
    @Before(Tx.class)
    public boolean addUserRoleService(String userId, Long[] roleId) {
        //授权角色时候，角色不能为null
        if (StrUtil.isBlank(userId) || roleId == null || roleId.length < 1) {
            return false;
        }

        String deleteSql = "delete from user_role where user_id=?";
        Db.delete(deleteSql, userId);
        List<UserRole> userRoleList = new ArrayList<>();
        for (Long s : roleId) {
            UserRole userRole = new UserRole();
            userRole.setRoleId(s);
            userRole.setUserId(userId);
            userRoleList.add(userRole);
        }
        return Db.batchSave(userRoleList, userRoleList.size()).length > 0;
    }


    /**
     * 删除指定用角色的指定权限
     *
     * @param rolePermission 角色权限对象
     * @return 操作结果
     */
    public boolean deleteRolePermissionService(RolePermission rolePermission) {

        return rolePermission.delete();
    }

    /**
     * 给指定的角色授权
     *
     * @param menuId       菜单Id集合
     * @param permissionId 权限Id集合
     * @param roleId       角色Id
     * @return 授权结果
     */
    @Before(Tx.class)
    public boolean addRolePermissionService(Long[] menuId, Long[] permissionId, Long roleId) {

        //删除角色菜单的sql语句
        String menuSql = "delete from role_menu where role_id=?";
        //删除角色权限的sql
        String permissionSql = "delete from role_permission where role_id=?";
        Db.delete(menuSql, roleId);
        Db.delete(permissionSql, roleId);

        Set<RoleMenu> roleMenuSet = new HashSet<>();
        //组装菜单权限对象
        for (Long s : menuId) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setMid(s);
            roleMenu.setRoleId(roleId);
            roleMenuSet.add(roleMenu);
        }
        List<RoleMenu> roleMenuList = new ArrayList<>();
        roleMenuList.addAll(roleMenuSet);
        //批量添加菜单权限
        Db.batchSave(roleMenuList, roleMenuList.size());
        //批量添加业务权限
        Set<RolePermission> rolePermissionSet = new HashSet<>();
        //组装菜单权限对象
        for (Long s : permissionId) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setPermissionId(s);
            rolePermission.setRoleId(roleId);
            rolePermissionSet.add(rolePermission);
        }
        //将set 转为List
        List<RolePermission> rolePermissionList1 = new ArrayList<>();
        rolePermissionList1.addAll(rolePermissionSet);
        Db.batchSave(rolePermissionList1, rolePermissionSet.size());

        return true;
    }

    /**
     * 查询全部的角色列表
     *
     * @return 角色列表
     */
    public List<Role> listRoleListService() {
        String sql = "select * from role";
        return Role.dao.find(sql);


    }

    /**
     * 给系统中添加角色
     *
     * @param role 角色对象
     * @return 操作结果
     */
    public boolean addRoleService(Role role) {
        role.setDate(new Date());
        return role.save();
    }

    /**
     * 删除指定的角色
     *
     * @param id 角色的唯一Id
     * @return 操作结果
     */
    public boolean deleteRoleService(String id) {
        return !StrUtil.isBlank(id) && Role.dao.deleteById(id);

    }

    /**
     * 根据角色来查询权限
     *
     * @param id 角色Id
     * @return 权限列表
     */
    public List<Permission> listRolePermissionService(String id) {
        if (StrUtil.isBlank(id)) {
            return null;
        }
        String sql = "select * from permission where id in (select permission_id from role_permission where role_id=?)";
        return Permission.dao.find(sql, id);
    }

    /**
     * 根据角色来查询权限
     *
     * @param id 角色Id
     * @return 权限列表
     */
    public List<Menu> listRoleMenuService(String id) {
        if (StrUtil.isBlank(id)) {
            return null;
        }
        String sql = "select * from menu where id in (select mid from role_menu where role_id=?)";
        return Menu.dao.find(sql, id);
    }

    /**
     * 全部的菜单信息
     *
     * @return 菜单列表
     */
    public List<Menu> listMenuService() {
        String sql = "select * from menu";
        return Menu.dao.find(sql);
    }

    /**
     * 添加菜单
     *
     * @param menu 菜单对象
     * @return 操作结果
     */
    public boolean addMenuService(Menu menu) {

        return menu.save();
    }

    /***
     * 删除指定的菜单
     * @param id 菜单唯一ID
     * @return 操作结果
     */
    public boolean deleteMenuService(String id) {
        if (StrUtil.isBlank(id)) {
            return false;
        }
        String sql = "delete from menu where id =? or fid=? ";
        return Db.delete(sql, id, id) > 0;
    }

    /**
     * 更新菜单信息
     *
     * @param menu 修改后的菜单信息
     * @return 操作结果
     */
    public boolean updateMenuService(Menu menu) {
        return menu.update();
    }

    /**
     * 用户角色查询
     */
    public List<Record> listUserRoleService() {
        String sql = "SELECT u.id,u.name,r.name as rname from user as u LEFT  JOIN user_role as ur ON u.id=ur.user_id LEFT JOIN role as r ON r.id=ur.role_id";
        List<Record> list = Db.find(sql);
        //下面都是去重操作
        Map<String, Record> map = new HashMap<>(list.size());
        list.forEach(record -> {
            String o = record.get("id");
            if (o == null) {
                map.put(o, record);
            } else {
                if (map.get(o) != null) {
                    record.set("rname", map.get(o).get("rname") + "," + record.get("rname"));
                } else {
                    record.set("rname", record.get("rname"));
                }
                map.put(record.get("id"), record);
            }
        });
        list.clear();
        map.forEach((key, value) -> list.add(value));
        return list;
    }
}
