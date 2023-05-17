package com.chen.medical.dto.cmn;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *  字典导出
 * </p>
 *
 * @author MaybeBin
 * @since 2023-05-17
 */
@Data
public class DictExportDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "id", index = 0)
    private Long id;

    @ExcelProperty(value = "上级id", index = 1)
    private Long parentId;

    @ExcelProperty(value = "名称", index = 2)
    private String name;

    @ExcelProperty(value = "值", index = 3)
    private Long value;

    @ExcelProperty(value = "编码", index = 4)
    private String dictCode;

}
