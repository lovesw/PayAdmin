package com.pay.data.utils;

/**
 * @createTime: 2017/12/28
 * @author: HingLo
 * @description: 字段常量定义
 */
public class FieldUtils {

    /**
     * 定义token的请求头
     */
    public static final String AUTHORIZATION = "Authorization";
    /**
     * 主题状态为待审核
     */
    public static final Integer THEME_STATUS_0 = 0;
    /**
     * 通过
     */
    public static final Integer THEME_STATUS_1 = 1;
    /**
     * 主题的状态为未通过
     */
    public static final Integer THEME_STATUS_2 = 2;

    /**
     * 惩罚记录默认的状态
     */
    public static final Integer PUNISH_STATUS_0 = 0;
    /**
     * 同意
     */
    public static final Integer PUNISH_STATUS_1 = 1;
    /**
     * 反对
     */
    public static final Integer PUNISH_STATUS_2 = 2;

    /**
     * PDF文件保存位置
     */
    public static final Integer FILE_PDF = 1;
    /**
     * 头像保存的位置
     */
    public static final int HEADER = 2;
    /**
     * 证件照保存的位置
     */
    public static final int CARD = 3;
    /**
     * 发票管理的文件位置
     */
    public static final int INVOICE = 4;


    /***
     * 上传文件的后缀
     */
    public static final String SUFFIX = ".pdf";


    /**
     * 定义设计师的角色编号默认为5
     */
    public static Long DESIGN = 5L;
}
