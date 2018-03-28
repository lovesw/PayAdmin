package com.pay.user.action;

import cn.hutool.core.codec.Base64;
import com.jfinal.aop.Before;
import com.pay.data.controller.BaseController;
import com.pay.data.interceptors.Get;
import com.pay.data.utils.FileImageUtils;
import com.pay.user.service.ImageService;

/**
 * @createTime: 2018/3/20
 * @author: HingLo
 * @description: 图片访问
 */
public class ImageAction extends BaseController {
    private final ImageService imageService = new ImageService();

    /**
     * 单独的图片请求
     *
     * @param type 图片类型
     * @param card 其他参数,此参数用于表示身份证的正面与反面
     */
    @Before(Get.class)
    public void image(int type, int card) {
        String userId = getUserId();
        String fileName = imageService.findImageNameService(userId, card, type);
        System.out.println(fileName);
        byte[] inputStream = FileImageUtils.readImageUtils(fileName, type);
        if (inputStream != null) {
            success(Base64.encode(inputStream));
        } else {
            //用于表示不做任何返回，这样就不会出现自动去找响应的HTML了
            renderNull();
        }
    }


}
