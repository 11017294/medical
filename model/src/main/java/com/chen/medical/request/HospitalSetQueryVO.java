package com.chen.medical.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *  医院信息请求参数
 * </p>
 *
 * @author MaybeBin
 * @since 2023-05-11
 */
@Data
public class HospitalSetQueryVO {

    @ApiModelProperty("医院名称")
    private String hosName;

    @ApiModelProperty("医院编号")
    private String hosCode;

}
