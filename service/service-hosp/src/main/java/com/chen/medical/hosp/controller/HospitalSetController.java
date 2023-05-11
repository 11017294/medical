package com.chen.medical.hosp.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.medical.hosp.service.HospitalSetService;
import com.chen.medical.model.hosp.HospitalSet;
import com.chen.medical.request.HospitalSetQueryVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 医院设置表 前端控制器
 * </p>
 *
 * @author MaybeBin
 * @since 2023-05-11
 */
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
@Api(value = "医院设置接口", tags = {"医院设置接口"})
public class HospitalSetController {

    @Autowired
    private HospitalSetService hospitalSetService;

    @PostMapping("add")
    @ApiOperation(value = "添加医院信息", response = HospitalSet.class)
    public Long addHospSet(HospitalSet hospitalSet){
        boolean save = hospitalSetService.save(hospitalSet);
        return hospitalSet.getId();
    }

    @GetMapping("findAll")
    @ApiOperation(value = "获取所有医院信息", response = HospitalSet.class)
    public List<HospitalSet> findAllHospitalSet() {
        return hospitalSetService.list();
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "删除医院信息", response = boolean.class)
    public boolean removeHospitalSet(@PathVariable Long id){
        return hospitalSetService.removeById(id);
    }

    @GetMapping("{id}")
    @ApiOperation(value = "按id查询医院信息", response = HospitalSet.class)
    public HospitalSet findHospitalSetById(@PathVariable Long id){
        return hospitalSetService.getById(id);
    }

    @PostMapping("findPage/{current}/{limit}")
    @ApiOperation(value = "分页查询医院信息", response = Page.class)
    public Page<HospitalSet> findPage(@PathVariable long current,
                                      @PathVariable long limit,
                                      @RequestBody(required = false) HospitalSetQueryVO hospitalSetQueryVO){
        return hospitalSetService.findPage(current, limit, hospitalSetQueryVO);
    }

    @GetMapping("editHosp")
    @ApiOperation(value = "修改医院信息", response = HospitalSet.class)
    public HospitalSet editHosp(HospitalSet hospitalSet){
        return hospitalSet;
    }

}
