package com.pay.user.service;

import cn.hutool.core.util.StrUtil;
import com.pay.data.utils.FieldUtils;
import com.pay.user.action.UserInfoAction;

import static com.pay.user.action.UserInfoAction.CARD1;
import static com.pay.user.action.UserInfoAction.CARD2;


/**
 * @createTime: 2018/3/20
 * @author: HingLo
 * @description: 图片访问的服务层
 */
public class ImageService {

    /***
     * 图片访问处理的服务层，主要是通过传递的参数来获取图片的名称
     * @param userId 员工的
     * @param card
     * @param type
     * @return
     */
    public String findImageNameService(String userId, int card, int type) {
        if (StrUtil.isNotBlank(userId)) {
            //如果身份证
            if (type == FieldUtils.CARD) {
                switch (card) {
                    case 1:
                        return userId + CARD1;
                    case 2:
                        return userId + CARD2;
                    default:
                        return null;
                }

            }          //其他类型的图片处理，在此处处理
            else if (type == FieldUtils.HEADER) {
                return userId + UserInfoAction.HEADER;
            }

        }
        return null;
    }
}
