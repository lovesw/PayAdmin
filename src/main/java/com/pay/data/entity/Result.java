package com.pay.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * @createTime: 2018/1/4
 * @author: HingLo
 * @description: 结果返回实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Result<T> {
    private Integer code;
    private String msg;
    private T data;
}
