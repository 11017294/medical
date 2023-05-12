package com.chen.medical.common.exception;

import com.chen.medical.common.enums.ErrorCode;
import com.chen.medical.common.result.BaseResponse;
import com.chen.medical.common.util.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * <p>
 *  全局异常处理
 * </p>
 *
 * @author MaybeBin
 * @since 2023-05-11
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public BaseResponse<String> handleBusinessException(BusinessException e) {
        log.error("BusinessException", e);
        return ResultUtils.error(e.getCode(), e.getMessage());
    }

    /**
     * 运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<String> handleRuntimeException(RuntimeException e) {
        log.error("RuntimeException", e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR.getCode(), ErrorCode.SYSTEM_ERROR.getMessage());
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(BindException.class)
    public BaseResponse<String> handleBindException(BindException e){
        String message = e.getAllErrors().get(0).getDefaultMessage();
        log.error("BindException：{}", message);
        return ResultUtils.error(ErrorCode.PARAM_ERROR.getCode(), message);
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        log.error("MethodArgumentNotValidException：{}", message);
        return ResultUtils.error(ErrorCode.PARAM_ERROR.getCode(), message);
    }

}
