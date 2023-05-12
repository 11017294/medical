package com.chen.medical.hosp.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.medical.model.hosp.HospitalSet;
import com.chen.medical.request.HospitalSetRequest;

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
}
