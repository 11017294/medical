package com.chen.medical.common.exception;

import com.chen.medical.common.enums.ErrorCode;
import lombok.Data;

/**
 * <p>
 *  业务异常类
 * </p>
 *
 * @author MaybeBin
 * @since 2023-05-11
 */
@Data
public class BusinessException extends RuntimeException {

    /**
     * 状态码
     */
    private final int code;

    /**
     * 信息
     */
    private final String message;

    public BusinessException(int code, String message){
        this.code = code;
        this.message = message;
    }

    public BusinessException(String message){
        this.code = ErrorCode.SYSTEM_ERROR.getCode();
        this.message = message;
    }

    public BusinessException(ErrorCode errorCode){
        this(errorCode.getCode(), errorCode.getMessage());
    }

}
