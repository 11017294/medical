package com.chen.medical.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.chen.medical.hosp.repository.ScheduleRepository;
import com.chen.medical.hosp.service.HospitalSetService;
import com.chen.medical.hosp.service.ScheduleService;
import com.chen.medical.model.hosp.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 排班
 * </p>
 *
 * @author MaybeBin
 * @since 2023-05-23
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private HospitalSetService hospitalSetService;

    @Override
    public void remove(Map<String, Object> paramMap) {
        hospitalSetService.verifySignature(paramMap);

        //获取医院编号和排班编号
        String hoscode = (String)paramMap.get("hoscode");
        String hosScheduleId = (String)paramMap.get("hosScheduleId");

        Schedule schedule = scheduleRepository.getScheduleByHoscodeAndHosScheduleId(hoscode, hosScheduleId);

        if(Objects.nonNull(schedule)) {
            scheduleRepository.deleteById(schedule.getId());
        }
    }

    @Override
    public Page<Schedule> findPageSchedule(Map<String, Object> paramMap) {
        hospitalSetService.verifySignature(paramMap);

        String hoscode = (String)paramMap.get("hoscode");
        String depcode = (String)paramMap.get("depcode");
        Integer page = Integer.parseInt((String) paramMap.getOrDefault("page", 1));
        Integer limit = Integer.parseInt((String) paramMap.getOrDefault("limit", 10));

        // 创建查询条件对象
        Schedule schedule = new Schedule();
        schedule.setHoscode(hoscode);
        schedule.setDepcode(depcode);
        schedule.setIsDeleted(0);

        // 构建分页
        Pageable pageable = PageRequest.of(page - 1, limit);
        // 构建条件
        ExampleMatcher example = ExampleMatcher
                .matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase();

        Example<Schedule> matcherExample = Example.of(schedule, example);

        return scheduleRepository.findAll(matcherExample, pageable);
    }

    @Override
    public void save(Map<String, Object> paramMap) {
        hospitalSetService.verifySignature(paramMap);

        String paramMapString = JSONObject.toJSONString(paramMap);
        Schedule schedule = JSONObject.parseObject(paramMapString,Schedule.class);

        // 根据医院编号 和 排班编号查询
        Schedule scheduleExist = scheduleRepository.
                getScheduleByHoscodeAndHosScheduleId(schedule.getHoscode(),schedule.getHosScheduleId());

        // 赋值
        schedule.setUpdateTime(new Date());
        schedule.setIsDeleted(0);
        schedule.setStatus(1);

        if(Objects.isNull(scheduleExist)){
            schedule.setCreateTime(new Date());
        } else {
            schedule.setId(scheduleExist.getId());
            schedule.setCreateTime(scheduleExist.getCreateTime());
        }

        scheduleRepository.save(schedule);
    }
}
