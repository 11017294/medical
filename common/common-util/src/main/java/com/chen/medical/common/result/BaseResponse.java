package com.chen.medical.common.result;


import lombok.Data;

/**
 * <p>
 *  通用返回类
 * </p>
 *
 * @author MaybeBin
 * @since 2023-05-11
 */
@Data
public class BaseResponse<T> {

    private int code;
    private T data;
    private String message;

    public BaseResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

}
