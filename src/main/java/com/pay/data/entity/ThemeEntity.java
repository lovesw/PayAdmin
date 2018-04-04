package com.pay.data.entity;

import lombok.Data;

import java.util.Date;

/**
 * @createTime: 2018/4/4
 * @author: HingLo
 * @description: 主题上传传输
 */
@Data
public class ThemeEntity {
    /**
     * 设计人的Id
     */
    private String designId;
    /**
     * 主题名称
     */
    private String name;
    /**
     * 主题的英语名称
     */
    private String ename;
    /**
     * 销售类型
     */
    private String moneyType;

    /**
     * 上架时间
     */
    private Date shelvesDate;

}
