package com.chen.medical.hosp.controller;

import com.chen.medical.hosp.service.ScheduleService;
import com.chen.medical.model.hosp.Schedule;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
 *  排班相关接口
 * </p>
 *
 * @author MaybeBin
 * @since 2023-05-25
 */
@Api(tags = "排班相关接口")
@RestController
@RequestMapping("admin/hosp/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("getScheduleRule/{page}/{limit}/{hoscode}/{depcode}")
    @ApiOperation(value = "分页查询医院信息")
    public Map<String, Object> findPage(@PathVariable Long page,
                                        @PathVariable Long limit,
                                        @PathVariable String hoscode,
                                        @PathVariable String depcode){
//        return scheduleService.getScheduleRuleByStream(page, limit, hoscode, depcode);
        return scheduleService.getScheduleRule(page, limit, hoscode, depcode);
    }

    @GetMapping("getScheduleDetail/{hoscode}/{depcode}/{workDate}")
    @ApiOperation(value = "查询当天排班详情信息")
    public List<Schedule> getScheduleDetails(
                                        @PathVariable String hoscode,
                                        @PathVariable String depcode,
                                        @PathVariable String workDate){
        return scheduleService.getScheduleDetails(hoscode, depcode, workDate);
    }

}
