package com.pay.user.service;

import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.upload.UploadFile;
import com.pay.data.utils.CommonUtils;
import com.pay.data.utils.FieldUtils;
import com.pay.data.utils.FileImageUtils;
import com.pay.data.utils.HttpClientUtils;
import com.pay.user.model.Role;
import com.pay.user.model.User;

import java.util.List;

/**
 * @createTime: 2018/3/20
 * @author: HingLo
 * @description: 用户信息管理的服务层
 */
public class UserInfoService {
    /**
     * 通过用户Id返回用户名称
     *
     * @param userId 员工编号
     * @return
     */
    public User userNameService(String userId) {
        return User.dao.findById(userId);
    }

    /**
     * 修改密码</br>
     * 需要开始事物
     *
     * @param userId      员工编号
     * @param password    新密码
     * @param oldPassword 旧密码
     * @return 修改结果
     */
    @Before(Tx.class)
    public boolean passwordService(String userId, String password, String oldPassword) {
        if (!CommonUtils.isNotBlank(userId, password, oldPassword)) {
            return false;
        }

        String sql = "update user set password=? where id=? and password=?";
        if (Db.update(sql, SecureUtil.md5(password), userId, SecureUtil.md5(oldPassword)) == 1) {
            return true;
        } else {
            throw new RuntimeException("密码修改失败");
        }

    }

    /**
     * 用户的全部信息
     *
     * @param userId 员工编号
     * @return 返回员工的全部信息
     */
    public Object userInfoAllService(String userId) {
        String sql = "select  u.* ,uf.* from user as u LEFT JOIN user_info as uf on uf.user_id=u.id and u.id=? where u.id=?";
        String sql1 = "select  u.*,(select name from department as d where d.id=u.department) as department,(select name from department as d where d.id=u.position) as position ,uf.* from user as u LEFT JOIN user_info as uf on uf.user_id=u.id and u.id=? where u.id=?";
        return Db.find(sql1, userId, userId);

    }

    /**
     * 指定的用户拥有的角色
     *
     * @param userId 用户角色ID
     * @return 角色名称
     */
    public String userRoleService(String userId) {
        String sql = "select * from role where id in (select role_id from user_role where user_id=?)";
        StringBuilder name = new StringBuilder();
        List<Role> list = Role.dao.find(sql, userId);
        for (Role role : list) {
            name.append(role.getName()).append(",");
        }
        if (StrUtil.isNotBlank(name)) {
            return name.substring(0, name.length() - 1);
        } else {
            return null;
        }


    }

    /**
     * 更新用户信息
     *
     * @param userId 用户信息的唯一Id
     * @param record 其他信息
     * @return 更新结果
     */
    public boolean userInfoUpdateService(String userId, Record record) {

        //如果是用户的不可以修改，就不修改
        User user = User.dao.findById(userId);
        if (user.getStatus()) {
            return false;
        }
        String idcard = record.get("idcard");
        record.set("user_id", userId);
        if (IdcardUtil.isvalidCard18(idcard)) {
            //获取身份证的信息
            String result = HttpClientUtils.getFormData("", idcard);
            if (HttpClientUtils.getCode(result)) {
                String place = (String) HttpClientUtils.getResult(result, "result.area");
                record.set("place", place);
                return Db.update("user_info", "user_id", record) && user.setStatus(false).update();
            }
        }
        return false;
    }

    /**
     * 跟换头像
     *
     * @param headerName 头像名称
     * @param header     头像地址
     * @return 修改结果
     */
    public boolean updateHeaderService(String headerName, UploadFile header) {
        String fileName = header.getOriginalFileName();
        //检测文件是图片图像并保存成功
        return FileImageUtils.iconUtils(fileName) && FileImageUtils.saveImageUtils(header, headerName, FieldUtils.HEADER);
    }

    /**
     * 保存身份证正面与反面
     *
     * @param card1     正面
     * @param cardName1 正面名称
     * @param card2     反面对象
     * @param cardName2 反面名称
     * @return 保存结果
     */
    public boolean updateCardService(UploadFile card1, String cardName1, UploadFile card2, String cardName2) {
        return card1 != null && card2 != null && FileImageUtils.iconUtils(card1.getOriginalFileName()) && FileImageUtils.saveImageUtils(card1, cardName1, FieldUtils.CARD) && FileImageUtils.iconUtils(card2.getOriginalFileName()) && FileImageUtils.saveImageUtils(card2, cardName2, FieldUtils.CARD);

    }
}
