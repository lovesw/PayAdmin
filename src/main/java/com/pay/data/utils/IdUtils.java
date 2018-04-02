package com.pay.data.utils;

import cn.hutool.core.util.RandomUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


/**
 * @createTime: 2018/1/3
 * @author: HingLo
 * @description: ID生成工具
 */
public class IdUtils {

    /**
     * 普通Id生成器,用时间戳随机打乱+指定位随机数生成，
     *
     * @return
     */
    public static String getId() {
        //获取当前时间戳
        String str = String.valueOf(System.currentTimeMillis());
        List<String> list = new ArrayList<>(13);
        //将时间戳放入到List中
        for (Character s : str.toCharArray()) {
            list.add(s.toString());
        }
        //随机打乱
        Collections.shuffle(list);
        //拼接字符串，并添加2(自定义)位随机数

        return String.join("", list) + RandomUtil.randomNumbers(2);
    }


    /**
     * 生成指定长度的一个数字字符串
     *
     * @param num 指定长度的随机数
     * @return 随机数值
     */
    public static String randomNumber(int num) {
        if (num < 1) {
            num = 1;
        }
        final StringBuilder str = new StringBuilder();
        for (int i = 0; i < num; i++) {
            str.append(ThreadLocalRandom.current().nextInt(10));
        }
        return str.toString();
    }


}
