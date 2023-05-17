package com.chen.medical.cmn.controller;


import com.chen.medical.cmn.service.DictService;
import com.chen.medical.common.util.ExportUtil;
import com.chen.medical.dto.cmn.DictExportDTO;
import com.chen.medical.model.cmn.Dict;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 字典表 前端控制器
 * </p>
 *
 * @author MaybeBin
 * @since 2023-05-16
 */
@RestController
@RequestMapping("/admin/cmn/dict")
@Api(value = "字典相关接口", tags = "字典相关接口")
public class DictController {

    @Autowired
    private DictService dictService;

    @ApiOperation(value = "根据数据id查询子数据列表")
    @GetMapping("findChildData/{id}")
    public List<Dict> findChildData(@PathVariable Long id) {
        return dictService.findChildData(id);
    }

    @ApiOperation(value="导出字典数据")
    @GetMapping("/exportDictData")
    public void exportDictData(HttpServletResponse response) throws IOException {
        List<DictExportDTO> list = dictService.exportDictData();
        ExportUtil.exportExcel(list, response, "字典", DictExportDTO.class);
    }

    @ApiOperation(value="导入字典数据")
    @PostMapping("/importData")
    public void importData(MultipartFile file){
        dictService.batchImport(file);
    }

}
