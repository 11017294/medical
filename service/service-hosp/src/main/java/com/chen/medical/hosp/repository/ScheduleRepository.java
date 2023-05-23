package com.chen.medical.hosp.repository;

import com.chen.medical.model.hosp.Schedule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  排班
 * </p>
 *
 * @author MaybeBin
 * @since 2023-05-23
 */
@Repository
public interface ScheduleRepository extends MongoRepository<Schedule, String> {

    Schedule getScheduleByHoscodeAndHosScheduleId(String hoscode, String hosScheduleId);
}
