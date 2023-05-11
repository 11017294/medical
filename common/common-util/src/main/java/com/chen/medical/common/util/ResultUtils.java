package com.chen.medical.common.util;

import com.chen.medical.common.enums.ErrorCode;
import com.chen.medical.common.result.BaseResponse;

/**
 * <p>
 *  返回类 工具类
 * </p>
 *
 * @author MaybeBin
 * @since 2023-05-11
 */
public class ResultUtils {

    /**
     * 返回成功
     * @return
     */
    public static <T> BaseResponse<T> success() {
        return new BaseResponse<>(200, null, "成功");
    }

    /**
     * 返回成功
     * @param data
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(200, data, "成功");
    }

    /**
     * 错误
     * @param code
     * @param errorMessage
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> error(int code, String errorMessage) {
        return new BaseResponse<>(code, null, errorMessage);
    }

    /**
     * 错误
     * @param errorMessage
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> error(String errorMessage) {
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR.getCode() ,errorMessage);
    }
}
