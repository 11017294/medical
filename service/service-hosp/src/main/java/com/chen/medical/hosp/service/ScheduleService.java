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

    Map<String, Object> findPageByStream(Integer page, Integer limit, String hoscode, String depcode);
}
