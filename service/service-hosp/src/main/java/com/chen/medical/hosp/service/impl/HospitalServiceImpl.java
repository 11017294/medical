package com.chen.medical.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.chen.medical.hosp.repository.HospitalRepository;
import com.chen.medical.hosp.service.HospitalService;
import com.chen.medical.hosp.service.HospitalSetService;
import com.chen.medical.model.hosp.Hospital;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 *  医院相关服务
 * </p>
 *
 * @author MaybeBin
 * @since 2023-05-22
 */
@Service
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private HospitalSetService hospitalSetService;

    @Override
    public void save(Map<String, Object> paramMap) {
        hospitalSetService.verifySignature(paramMap);

        String str = JSONObject.toJSONString(paramMap);
        Hospital hospital = JSONObject.parseObject(str, Hospital.class);

        // 判断是否存在数据
        String hoscode = hospital.getHoscode();
        Hospital hospitalExist = hospitalRepository.getHospitalByHoscode(hoscode);

        // 给对象赋值
        hospital.setUpdateTime(new Date());
        hospital.setIsDeleted(0);
        String logoData = hospital.getLogoData().replace(" ", "+");
        hospital.setLogoData(logoData);

        if (Objects.isNull(hospitalExist)){
            hospital.setStatus(0);
            hospital.setCreateTime(new Date());
        } else {
            hospital.setId(hospitalExist.getId());
            hospital.setStatus(hospitalExist.getStatus());
            hospital.setCreateTime(hospitalExist.getCreateTime());
        }
        hospitalRepository.save(hospital);
    }

    @Override
    public Hospital getHospital(Map<String, Object> paramMap) {
        hospitalSetService.verifySignature(paramMap);

        String hoscode = (String) paramMap.get("hoscode");

        return hospitalRepository.getHospitalByHoscode(hoscode);
    }

}
