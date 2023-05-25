package com.chen.medical.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.chen.medical.cmn.client.DictFeignClient;
import com.chen.medical.common.enums.ErrorCode;
import com.chen.medical.common.exception.BusinessException;
import com.chen.medical.hosp.repository.HospitalRepository;
import com.chen.medical.hosp.service.HospitalService;
import com.chen.medical.hosp.service.HospitalSetService;
import com.chen.medical.model.hosp.Hospital;
import com.chen.medical.request.hosp.HospitalRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

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
    @Autowired
    private DictFeignClient dictFeignClient;

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

    @Override
    public Page<Hospital> getHospitalPage(Integer page, Integer limit, HospitalRequest hospitalRequest) {
        // 构建分页
        Pageable pageable = PageRequest.of(page - 1, limit);
        // 构建条件
        ExampleMatcher example = ExampleMatcher
                .matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase();

        // 拷贝参数对象
        Hospital hospital = new Hospital();
        BeanUtils.copyProperties(hospitalRequest, hospital);

        Example<Hospital> matcherExample = Example.of(hospital, example);
        Page<Hospital> hospitalsPage = hospitalRepository.findAll(matcherExample, pageable);
        hospitalsPage.getContent().stream().forEach(item -> {
            setHospitalHosType(item);
        });

        return hospitalsPage;
    }

    @Override
    public void updateStatus(String id, Integer status) {
        Hospital hospital = getHospital(id);
        hospital.setStatus(status);
        hospital.setUpdateTime(new Date());

        hospitalRepository.save(hospital);
    }

    @Override
    public Hospital getHospitalById(String id) {
        Hospital hospital = getHospital(id);
        setHospitalHosType(hospital);
        return hospital;
    }

    @Override
    public String getHospName(String hoscode) {
        Hospital hospitalExist = hospitalRepository.getHospitalByHoscode(hoscode);
        return hospitalExist.getHosname();
    }

    private Hospital getHospital(String id){
        Optional<Hospital> hospitalOptional = hospitalRepository.findById(id);
        if(!hospitalOptional.isPresent()){
            throw new BusinessException(ErrorCode.NOT_FOUND);
        }
        return hospitalOptional.get();
    }

    /**
     * 设置医院等级
     * @param hospital
     */
    private void setHospitalHosType(Hospital hospital){
        String hostypeString = dictFeignClient.getDictName("Hostype", hospital.getHostype());
        String dictName = dictFeignClient.getDictName("Hostype");
        hospital.getParam().put("hostypeString", hostypeString);
        hospital.getParam().put("dictName", dictName);
        hospital.getParam().put("fullAddress", dictName);
    }

}
