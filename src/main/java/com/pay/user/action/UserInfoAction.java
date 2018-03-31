package com.pay.user.action;

import cn.hutool.core.codec.Base64;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;
import com.pay.data.controller.BaseController;
import com.pay.data.interceptors.Get;
import com.pay.data.interceptors.Post;
import com.pay.data.utils.FileImageUtils;
import com.pay.data.utils.FieldUtils;
import com.pay.user.model.User;
import com.pay.user.service.UserInfoService;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @createTime: 2018/3/20
 * @author: HingLo
 * @description: 用户信息管理
 */
public class UserInfoAction extends BaseController {
    private final UserInfoService userInfoService = new UserInfoService();
    /**
     * 定义头像
     */
    public static final String HEADER = "header.png";
    /**
     * 证件照的正面
     */
    public static final String CARD1 = "card1.png";
    /**
     * 证件照的反面
     */
    public static final String CARD2 = "card2.png";

    /**
     * 登录后获取用户的名称，用户头像
     */
    @Before(Get.class)
    public void userInfo() {
        String userId = getUserId();
        User user = userInfoService.userNameService(userId);
        byte[] inputStream = FileImageUtils.readImageUtils(userId + HEADER, FieldUtils.HEADER);
        Map<String, Object> map = new HashMap<>(2);
        map.put("name", user.getName());
        if (inputStream != null) {
            map.put("header", Base64.encode(inputStream));
        } else {
            map.put("header", null);
        }
        success(map);
    }

    /**
     * 用户修改密码
     *
     * @param password    用户的新密码
     * @param oldPassword 用户的原来的密码
     */
    @Before(Post.class)
    public void password(String password, String oldPassword) {
        String userId = getUserId();
        boolean bool = userInfoService.passwordService(userId, password, oldPassword);
        result(bool, "密码修改失败");
    }

    /**
     * 获取员工的所有的信息
     */
    @Before(Get.class)
    public void userInfoAll() {
        String userId = getUserId();
        success(userInfoService.userInfoAllService(userId));
    }

    /**
     * 查看用户角色信息
     */
    @Before(Get.class)
    public void userRole() {
        String userId = getUserId();
        success(userInfoService.userRoleService(userId));
    }

    /**
     * 修改用户的信息
     */
    @Before(Post.class)
    public void userInfoUpdate() {
        Record record = new Record();
        Enumeration<String> enumeration = getParaNames();
        while (enumeration.hasMoreElements()) {
            String params = enumeration.nextElement();
            record.set(params, getPara(params));
        }
        String userId = getUserId();
        success(userInfoService.userInfoUpdateService(userId, record));
    }

    /**
     * 修改头像
     *
     * @param header 头像的图片对象
     */
    @Before(Post.class)
    public void updateHeader(UploadFile header) {
        String userId = getUserId();
        result(userInfoService.updateHeaderService(userId + HEADER, header), "头像更换失败");
    }

    /**
     * 上传身份证照片
     *
     * @param card1 证件照的正面
     * @param card2 证件照的反面
     */
    @Before(Post.class)
    public void updateCard(UploadFile card1, UploadFile card2) {
        String userId = getUserId();
        result(userInfoService.updateCardService(card1, userId + CARD1, card2, userId + CARD2), "证件照上传失败");
    }

}
