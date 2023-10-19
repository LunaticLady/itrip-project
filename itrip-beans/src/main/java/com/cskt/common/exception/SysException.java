package com.cskt.common.exception;

import com.cskt.common.constants.ErrorCodeEnum;
import lombok.Data;

/**
 * 自定义系统异常
 */
@Data
public class SysException extends RuntimeException {
    private String errorCode;//异常错误码

    public SysException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public SysException(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getMsg());
        this.errorCode = errorCodeEnum.getErrorCode();
    }
}
