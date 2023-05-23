package com.chen.medical.hosp.controller.api;

import com.chen.medical.helper.HttpRequestHelper;
import com.chen.medical.hosp.service.DepartmentService;
import com.chen.medical.hosp.service.HospitalService;
import com.chen.medical.hosp.service.ScheduleService;
import com.chen.medical.model.hosp.Department;
import com.chen.medical.model.hosp.Hospital;
import com.chen.medical.model.hosp.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author MaybeBin
 * @since 2023-05-22
 */
@RestController
@RequestMapping("/api/hosp")
public class ApiController {

    @Autowired
    private HospitalService hospitalService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private ScheduleService scheduleService;

    @PostMapping("saveHospital")
    public void saveHosp(HttpServletRequest request){
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        hospitalService.save(paramMap);
    }

    @PostMapping("hospital/show")
    public Hospital getHospital(HttpServletRequest request){
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        return hospitalService.getHospital(paramMap);
    }

    /**
     * 上传科室信息
     * @param request
     */
    @PostMapping("saveDepartment")
    public void saveDepartment(HttpServletRequest request){
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        departmentService.save(paramMap);
    }

    /***
     * 查找科室信息
     * @param request
     */
    @PostMapping("department/list")
    public Page<Department> findDepartment(HttpServletRequest request){
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        return departmentService.findDepartment(paramMap);
    }

    /**
     * 删除科室
     * @param request
     */
    @PostMapping("department/remove")
    public void removeDepartment(HttpServletRequest request){
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        departmentService.removeDepartment(paramMap);
    }

    /**
     * 删除排班
     * @param request
     */
    @PostMapping("schedule/remove")
    public void remove(HttpServletRequest request) {
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        scheduleService.remove(paramMap);
    }

    /**
     * 查询排班接口
     * @param request
     * @return
     */
    @PostMapping("schedule/list")
    public Page<Schedule> findSchedule(HttpServletRequest request) {
        //获取传递过来科室信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        return scheduleService.findPageSchedule(paramMap);
    }

    /**
     * 上传排班接口
     * @param request
     */
    @PostMapping("saveSchedule")
    public void saveSchedule(HttpServletRequest request) {
        //获取传递过来科室信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        scheduleService.save(paramMap);
    }

}
