package com.chen.medical.hosp.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.medical.common.exception.BusinessException;
import com.chen.medical.model.hosp.HospitalSet;
import com.chen.medical.request.hosp.HospitalSetRequest;

import java.util.Map;

/**
 * <p>
 * 医院设置表 服务类
 * </p>
 *
 * @author MaybeBin
 * @since 2023-05-11
 */
public interface HospitalSetService extends IService<HospitalSet> {

    /**
     * 分页查询医院信息
     * @param current
     * @param limit
     * @param hospitalSetRequest
     * @return
     */
    Page<HospitalSet> findPage(long current, long limit, HospitalSetRequest hospitalSetRequest);

    /**
     * 获取签名
     * @param hoscode
     * @return
     */
    String getSignByHoscode(String hoscode);

    /**
     * 验证签名
     * @param paramMap
     * @throws BusinessException 验证失败抛出异常
     */
    void verifySignature(Map<String, Object> paramMap) throws BusinessException;
}
