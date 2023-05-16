package com.chen.medical.cmn.controller;


import com.chen.medical.cmn.service.DictService;
import com.chen.medical.model.cmn.Dict;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    //根据数据id查询子数据列表
    @ApiOperation(value = "根据数据id查询子数据列表")
    @GetMapping("findChildData/{id}")
    public List<Dict> findChildData(@PathVariable Long id) {
        return dictService.findChildData(id);
    }

}
