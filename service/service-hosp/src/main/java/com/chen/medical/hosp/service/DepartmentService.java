package com.chen.medical.hosp.service;

import com.chen.medical.model.hosp.Department;
import org.springframework.data.domain.Page;

import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author MaybeBin
 * @since 2023-05-23
 */
public interface DepartmentService {

    void save(Map<String, Object> paramMap);

    Page<Department> findDepartment(Map<String, Object> paramMap);

    void removeDepartment(Map<String, Object> paramMap);
}
