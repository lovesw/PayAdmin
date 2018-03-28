package com.pay.data.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @createTime: 2018/2/25
 * @author: HingLo
 * @description: 驼峰转化下划线工具
 */
public class HumpToUnderline {

    /**
     * 下划线转为驼峰命名
     *
     * @param list
     * @return
     */
    public static List<Map<String, Object>> underlineToHump(List<Map<String, Object>> list) {

        List<Map<String, Object>> mapList = new ArrayList<>();
        for (Map<String, Object> map : list) {
            Map<String, Object> map1 = new HashMap<>(map.size());
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String oldName = entry.getKey();
                String newName = camelName(oldName);
                map1.put(newName, entry.getValue());
            }
            mapList.add(map1);
        }
        return mapList;
    }

    /**
     * 将list集合中的对象中含有下划线的方法名称转化为驼峰命名
     *
     * @param list
     * @param <M>
     * @return
     */
    public static <M> List<Map<String, Object>> underlineToHumps(List<M> list) {
        List<Map<String, Object>> list1 = new ArrayList<>();
        for (M m : list) {
            list1.add(strToMap(m));
        }
        return list1;
    }

    /***
     * 将指定的对象转为为Map对象
     * @param m 对象转化的驼峰名的对象
     * @return
     */
    private static Map<String, Object> strToMap(Object m) {

//        将对象转为 字符串
        String str = m.toString();
        Map<String, Object> stringObjectMap = new HashMap<>();
        //去掉字符串的 大括号{}
        String res = str.substring(1, str.length() - 1);
        /*将对象分离出来*/
        String[] resArray = res.split(",");
        for (String s : resArray) {
            String[] s1 = s.split(":", 2);
            String name = s1[0];
            //将对象的名称转化为驼峰名
            String resName = camelName(name);
            String rName = "get" + StrUtil.upperFirst(resName.trim());
            //获取该关键字的方法对象
            Method method = ReflectUtil.getMethod(m.getClass(), rName);
            stringObjectMap.put(resName.trim(), toValueOf(method, s1[1]));
        }
        return stringObjectMap;
    }


    /**
     * 转入指定的方法对象，将指定的字符串转化为方法的返回类型值
     *
     * @param method 方法
     * @param str    需要转化的值
     * @return
     */
    private static Object toValueOf(Method method, String str) {

        if (StrUtil.isBlank(str) || method == null) {
            return str;
        } else if (StrUtil.equals("null", str)) {
            return null;
        } else {
            // 方法的返回类型的class对象
            Class methodClass = method.getReturnType();

            if (methodClass == Integer.class) {
                return Integer.valueOf(str);
            } else if (methodClass == Long.class) {
                return Long.valueOf(str);
            } else if (methodClass == Date.class) {
                //如果是时间对象，返回时间戳
                return DateUtil.parse(str).getTime();
            } else if (methodClass == Boolean.class) {
                return Boolean.valueOf(str);
            } else {
                return str;
            }
        }
    }


    /**
     * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。</br>
     * 例如：HELLO_WORLD->HelloWorld
     *
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    private static String camelName(String name) {
        StringBuilder result = new StringBuilder();
        // 快速检查
        if (name == null || name.isEmpty()) {
            // 没必要转换
            return "";
        } else if (!name.contains("_")) {
            // 不含下划线，仅将首字母小写
            return name.substring(0, 1).toLowerCase() + name.substring(1);
        }
        // 用下划线将原始字符串分割
        String camels[] = name.split("_");
        for (String camel : camels) {
            // 跳过原始字符串中开头、结尾的下换线或双重下划线
            if (camel.isEmpty()) {
                continue;
            }
            // 处理真正的驼峰片段
            if (result.length() == 0) {
                // 第一个驼峰片段，全部字母都小写
                result.append(camel.toLowerCase());
            } else {
                // 其他的驼峰片段，首字母大写
                result.append(camel.substring(0, 1).toUpperCase());
                result.append(camel.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }

}
