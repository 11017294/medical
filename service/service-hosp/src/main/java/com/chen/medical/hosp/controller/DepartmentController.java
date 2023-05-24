package com.chen.medical.hosp.controller;

import com.chen.medical.hosp.service.DepartmentService;
import com.chen.medical.model.hosp.Department;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author MaybeBin
 * @since 2023-05-24
 */
@RestController
@RequestMapping("/admin/hosp/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    // 根据医院编号，查询医院所有科室列表
    @ApiOperation(value = "查询医院所有科室列表")
    @GetMapping("getDeptList/{hoscode}")
    public List<Department> getDeptList(@PathVariable String hoscode) {
        return departmentService.findDeptTree(hoscode);
    }

}
