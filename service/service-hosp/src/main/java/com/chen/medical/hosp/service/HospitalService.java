package com.chen.medical.hosp.service;

import com.chen.medical.model.hosp.Hospital;

import java.util.Map;

/**
 * <p>
 *  医院相关服务
 * </p>
 *
 * @author MaybeBin
 * @since 2023-05-22
 */
public interface HospitalService {

    void save(Map<String, Object> paramMap);

    Hospital getHospital(Map<String, Object> paramMap);
}
