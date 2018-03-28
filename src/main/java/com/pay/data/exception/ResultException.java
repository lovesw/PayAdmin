package com.pay.data.exception;


import com.pay.data.enums.ExceptionEnum;
import lombok.Data;

/**
 * @createTime: 2018/1/4
 * @author: HingLo
 * @description: 自定义异常
 */
@Data
public class ResultException extends RuntimeException {

    private Integer code;

    public ResultException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMessage());
        this.code = exceptionEnum.getCode();
    }

    public ResultException(int code, String msg) {
        super(msg);
        this.code = code;

    }

}
