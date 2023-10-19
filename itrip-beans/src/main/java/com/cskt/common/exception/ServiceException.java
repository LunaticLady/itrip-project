package com.cskt.common.exception;

import com.cskt.common.constants.ErrorCodeEnum;
import lombok.Data;

/**
 * 自定义Service层异常
 */
@Data
public class ServiceException extends RuntimeException {
    private String errorCode;//异常错误码

    public ServiceException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ServiceException(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getMsg());
        this.errorCode = errorCodeEnum.getErrorCode();
    }
}
