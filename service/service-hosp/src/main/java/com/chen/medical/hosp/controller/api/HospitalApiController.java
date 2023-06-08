package com.chen.medical.hosp.controller.api;

import com.chen.medical.hosp.service.DepartmentService;
import com.chen.medical.hosp.service.HospitalService;
import com.chen.medical.model.hosp.Hospital;
import com.chen.medical.request.hosp.HospitalRequest;
import com.chen.medical.vo.hosp.DepartmentVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author MaybeBin
 * @since 2023-06-08
 */
@Api(tags = "医院管理接口")
@RestController
@RequestMapping("/api/hosp/hospital")
public class HospitalApiController {

    @Autowired
    private HospitalService hospitalService;
    @Autowired
    private DepartmentService departmentService;

    @ApiOperation(value = "获取分页列表")
    @GetMapping("{page}/{limit}")
    public Page<Hospital> index(
            @PathVariable Integer page,
            @PathVariable Integer limit,
            HospitalRequest hospitalRequest) {
        return hospitalService.getHospitalPage(page, limit, hospitalRequest);
    }

    @ApiOperation(value = "根据医院名称获取医院列表")
    @GetMapping("findByHosname/{hosname}")
    public List<Hospital> findByHosname(
            @ApiParam(name = "hosname", value = "医院名称", required = true)
            @PathVariable String hosname) {
        return hospitalService.findByHosname(hosname);
    }

    @ApiOperation(value = "获取科室列表")
    @GetMapping("department/{hoscode}")
    public List<DepartmentVo> index(
            @ApiParam(name = "hoscode", value = "医院code", required = true)
            @PathVariable String hoscode) {
        return departmentService.findDeptTree(hoscode);
    }

    @ApiOperation(value = "医院预约挂号详情")
    @GetMapping("{hoscode}")
    public Map<String, Object> item(
            @ApiParam(name = "hoscode", value = "医院code", required = true)
            @PathVariable String hoscode) {
        return hospitalService.item(hoscode);
    }

}
