package com.cskt.common.exception;

import com.cskt.common.constants.ErrorCodeEnum;
import lombok.Data;

/**
 * 自定义Dao层异常
 */
@Data
public class DaoException extends RuntimeException {
    private String errorCode;//异常错误码

    public DaoException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public DaoException(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getMsg());
        this.errorCode = errorCodeEnum.getErrorCode();
    }
}
