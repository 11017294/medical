package com.chen.medical.hosp.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.medical.common.enums.ErrorCode;
import com.chen.medical.common.exception.BusinessException;
import com.chen.medical.hosp.mapper.HospitalSetMapper;
import com.chen.medical.hosp.service.HospitalSetService;
import com.chen.medical.model.hosp.HospitalSet;
import com.chen.medical.request.hosp.HospitalSetRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 医院设置表 服务实现类
 * </p>
 *
 * @author MaybeBin
 * @since 2023-05-11
 */
@Service
public class HospitalSetServiceImpl extends ServiceImpl<HospitalSetMapper, HospitalSet> implements HospitalSetService {

    @Override
    public Page<HospitalSet> findPage(long current, long limit, HospitalSetRequest hospitalSetRequest) {
        LambdaQueryWrapper<HospitalSet> wrapper = new LambdaQueryWrapper<>();
        String hosName = null;
        String hosCode = null;
        if (Objects.nonNull(hospitalSetRequest)) {
            hosName = hospitalSetRequest.getHosName();
            hosCode = hospitalSetRequest.getHosCode();
        }
        wrapper.like(StringUtils.isNotBlank(hosName), HospitalSet::getHosname, hosName)
                .eq(StringUtils.isNotBlank(hosCode), HospitalSet::getHoscode, hosCode);

        Page<HospitalSet> page = new Page<>(current, limit);
        return baseMapper.selectPage(page, wrapper);
    }

    @Override
    public String getSignByHoscode(String hoscode) {
        Assert.notNull(hoscode, "hoscode不能为null");

        LambdaQueryWrapper<HospitalSet> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HospitalSet::getHoscode, hoscode);
        wrapper.select(HospitalSet::getSignKey);

        HospitalSet hospitalSet = baseMapper.selectOne(wrapper);

        return hospitalSet.getSignKey();
    }

    @Override
    public void verifySignature(Map<String, Object> paramMap) throws BusinessException {
        String hoscode = (String) paramMap.getOrDefault("hoscode", "");
        String signKey = getSignByHoscode(hoscode);

        String sign = (String) paramMap.getOrDefault("sign", "");
        String locatSign = SecureUtil.md5(signKey);

        // 不一致抛异常
        if(!Objects.equals(sign, locatSign)){
            throw new BusinessException(ErrorCode.SIGN_ERROR);
        }
    }
}
