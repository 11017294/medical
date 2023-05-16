package com.chen.medical.model.cmn;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.chen.medical.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 组织架构表
 * </p>
 *
 * @author MaybeBin
 * @since 2023-05-16
 */
@Data
@TableName("dict")
@ApiModel(value = "Dict对象", description = "组织架构表")
public class Dict extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("上级id")
    @TableField("parent_id")
    private Long parentId;

    @ApiModelProperty("名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("值")
    @TableField("value")
    private Long value;

    @ApiModelProperty("编码")
    @TableField("dict_code")
    private String dictCode;

}
