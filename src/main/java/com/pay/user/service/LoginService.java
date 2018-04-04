package com.pay.user.service;

import cn.hutool.crypto.SecureUtil;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.pay.data.utils.CommonUtils;
import com.pay.user.model.Menu;
import com.pay.user.model.Permission;
import com.pay.user.model.Role;
import com.pay.user.model.User;

import java.util.List;

/**
 * @createTime: 2018/3/1
 * @author: HingLo
 * @description: 登录
 */
public class LoginService {
    /**
     * 登录账号
     *
     * @param username 用户名，即唯一的员工编号
     * @param password 员工的唯一密码
     * @return 是否登录成功
     */
    public User loginMethodService(String username, String password) {

        if (CommonUtils.isNotBlank(username, password)) {
            String sql = "select * from user where id=? and password=? and mark=true";
            List<User> list = User.dao.find(sql, username, SecureUtil.md5(password));
            return list.isEmpty() ? null : list.get(0);
        }
        return null;
    }

    /**
     * 通过指定的用户名来查询全部的权限
     *
     * @param username 员工唯一id
     * @return 权限的集合
     */
    public List<Permission> findPermissionService(String username) {
        String sql = "select * from permission where  id in (select permission_id from role_permission where role_id in (select role_id from user_role where user_id=?) )";
        return Permission.dao.find(sql, username);
    }

    /**
     * @param username 用户的唯一Id
     * @return 返回该用户的全部菜单权限
     */
    public List<Menu> findMenuService(String username) {
        String sql = "select * from menu where id in (select mid from role_menu where role_id in (select role_id from user_role where user_id=? ))";
        return Menu.dao.find(sql, username);
    }

}
