package com.chen.medical.hosp.controller;

import com.chen.medical.hosp.service.HospitalService;
import com.chen.medical.model.hosp.Hospital;
import com.chen.medical.request.hosp.HospitalRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  医院相关接口
 * </p>
 *
 * @author MaybeBin
 * @since 2023-05-23
 */
@Api(tags = {"医院相关接口"})
@RestController
@RequestMapping("/admin/hosp/hospital")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @ApiOperation(value = "获取医院信息")
    @GetMapping("list/{page}/{limit}")
    public Page<Hospital> getHospitalPage(@PathVariable Integer page,
                                          @PathVariable Integer limit,
                                          HospitalRequest hospitalRequest) {
        return hospitalService.getHospitalPage(page, limit, hospitalRequest);
    }

    @ApiOperation(value = "更新上线状态")
    @PostMapping("updateStatus/{id}/{status}")
    public void lock(
            @ApiParam(name = "id", value = "医院id", required = true)
            @PathVariable("id") String id,
            @ApiParam(name = "status", value = "状态（0：未上线 1：已上线）", required = true)
            @PathVariable("status") Integer status){
        hospitalService.updateStatus(id, status);
    }

    @ApiOperation(value = "获取医院详情")
    @GetMapping("show/{id}")
    public Hospital show(
            @ApiParam(name = "id", value = "医院id", required = true)
            @PathVariable String id) {
        return hospitalService.getHospitalById(id);
    }
}
