package com.chen.medical.hosp.service;

import com.chen.medical.model.hosp.Schedule;
import org.springframework.data.domain.Page;

import java.util.Map;

/**
 * <p>
 * 排班
 * </p>
 *
 * @author MaybeBin
 * @since 2023-05-23
 */
public interface ScheduleService {

    void remove(Map<String, Object> paramMap);

    Page<Schedule> findPageSchedule(Map<String, Object> paramMap);

    void save(Map<String, Object> paramMap);

    /**
     * 使用 Stream 进行统计 排班
     * @param page
     * @param limit
     * @param hoscode
     * @param depcode
     * @return
     */
    Map<String, Object> getScheduleRuleByStream(Long page, Long limit, String hoscode, String depcode);

    /**
     * 使用 mongo api 进行聚合统计 排班
     * @param page
     * @param limit
     * @param hoscode
     * @param depcode
     * @return
     */
    Map<String, Object> getScheduleRule(Long page, Long limit, String hoscode, String depcode);
}
