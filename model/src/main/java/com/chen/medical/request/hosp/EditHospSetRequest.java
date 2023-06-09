package com.chen.medical.request.hosp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *  修改医院信息
 * </p>
 *
 * @author MaybeBin
 * @since 2023-05-12
 */
@Data
public class EditHospSetRequest {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty("医院名称")
    private String hosname;

    @ApiModelProperty("医院编号")
    private String hoscode;

    @ApiModelProperty("api基础路径")
    private String apiUrl;

    @ApiModelProperty("联系人")
    private String contactsName;

    @ApiModelProperty("联系人手机")
    private String contactsPhone;

}
