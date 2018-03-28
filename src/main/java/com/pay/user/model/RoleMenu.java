package com.pay.user.model;

import com.pay.user.bean.BaseRoleMenu;
import lombok.ToString;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
@ToString
public class RoleMenu extends BaseRoleMenu<RoleMenu> {
    public static final RoleMenu dao = new RoleMenu().dao();


    @Override
    public int hashCode() {
        return getRoleId().hashCode() + getMid().hashCode();
    }
}
