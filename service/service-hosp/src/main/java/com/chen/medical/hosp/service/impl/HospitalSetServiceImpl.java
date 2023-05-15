package com.chen.medical.hosp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.medical.hosp.mapper.HospitalSetMapper;
import com.chen.medical.hosp.service.HospitalSetService;
import com.chen.medical.model.hosp.HospitalSet;
import com.chen.medical.request.HospitalSetRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
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
}
