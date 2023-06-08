package com.chen.medical.hosp.repository;

import com.chen.medical.model.hosp.Hospital;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  医院mongo存储
 * </p>
 *
 * @author MaybeBin
 * @since 2023-05-22
 */
@Repository
public interface HospitalRepository extends MongoRepository<Hospital, String> {

    Hospital getHospitalByHoscode(String hoscode);

    List<Hospital> findHospitalByHosnameLike(String hosname);

}
