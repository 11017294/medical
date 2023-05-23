package com.chen.medical.hosp.repository;

import com.chen.medical.model.hosp.Department;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *
 * </p>
 *
 * @author MaybeBin
 * @since 2023-05-23
 */
@Repository
public interface DepartmentRepository extends MongoRepository<Department, String> {

    /**
     * 查询科室
     * @param hoscode
     * @param depcode
     * @return
     */
    Department getDepartmentByHoscodeAndDepcode(String hoscode, String depcode);

}
