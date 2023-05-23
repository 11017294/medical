package com.chen.medical.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.chen.medical.hosp.repository.DepartmentRepository;
import com.chen.medical.hosp.service.DepartmentService;
import com.chen.medical.hosp.service.HospitalSetService;
import com.chen.medical.model.hosp.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 *
 * </p>
 *
 * @author MaybeBin
 * @since 2023-05-23
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private HospitalSetService hospitalSetService;

    @Override
    public void save(Map<String, Object> paramMap) {
        hospitalSetService.verifySignature(paramMap);

        String str = JSONObject.toJSONString(paramMap);
        Department department = JSONObject.parseObject(str, Department.class);

        // 查询库中是否存在
        Department departmentExist = departmentRepository
                .getDepartmentByHoscodeAndDepcode(department.getHoscode(), department.getDepcode());

        // 赋值
        department.setUpdateTime(new Date());
        department.setIsDeleted(0);

        if(Objects.isNull(departmentExist)){
            department.setCreateTime(new Date());
        } else {
            department.setId(departmentExist.getId());
            department.setCreateTime(departmentExist.getCreateTime());
        }

        departmentRepository.save(department);
    }

    @Override
    public Page<Department> findDepartment(Map<String, Object> paramMap) {
        hospitalSetService.verifySignature(paramMap);

        Integer page = Integer.parseInt((String) paramMap.getOrDefault("page", 1));
        Integer limit = Integer.parseInt((String) paramMap.getOrDefault("limit", 10));
        String hoscode = (String) paramMap.get("hoscode");

        // 创建查询条件对象
        Department department = new Department();
        department.setHoscode(hoscode);
        department.setIsDeleted(0);

        // 构建分页
        Pageable pageable = PageRequest.of(page - 1, limit);
        // 构建条件
        ExampleMatcher example = ExampleMatcher
                .matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase();

        Example<Department> matcherExample = Example.of(department, example);

        return departmentRepository.findAll(matcherExample ,pageable);
    }

    @Override
    public void removeDepartment(Map<String, Object> paramMap) {
        hospitalSetService.verifySignature(paramMap);

        String hoscode = (String) paramMap.get("hoscode");
        String depcode = (String) paramMap.get("depcode");

        // 查询库中是否存在
        Department departmentExist = departmentRepository
                .getDepartmentByHoscodeAndDepcode(hoscode, depcode);

        if(Objects.nonNull(departmentExist)){
            departmentRepository.deleteById(departmentExist.getId());
        }

    }
}
