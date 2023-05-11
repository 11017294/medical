package com.chen.medical.model.hosp;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.chen.medical.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 医院设置表
 * </p>
 *
 * @author MaybeBin
 * @since 2023-05-11
 */
@Data
@TableName("hospital_set")
@ApiModel(value = "HospitalSet对象", description = "医院设置表")
public class HospitalSet extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("医院名称")
    @TableField("hosname")
    private String hosname;

    @ApiModelProperty("医院编号")
    @TableField("hoscode")
    private String hoscode;

    @ApiModelProperty("api基础路径")
    @TableField("api_url")
    private String apiUrl;

    @ApiModelProperty("签名秘钥")
    @TableField("sign_key")
    private String signKey;

    @ApiModelProperty("联系人")
    @TableField("contacts_name")
    private String contactsName;

    @ApiModelProperty("联系人手机")
    @TableField("contacts_phone")
    private String contactsPhone;

    @ApiModelProperty("状态")
    @TableField("status")
    private Integer status;

}
