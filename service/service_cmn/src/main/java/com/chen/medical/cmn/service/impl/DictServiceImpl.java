package com.chen.medical.cmn.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.medical.cmn.listener.DictListener;
import com.chen.medical.cmn.mapper.DictMapper;
import com.chen.medical.cmn.service.DictService;
import com.chen.medical.common.exception.BusinessException;
import com.chen.medical.dto.cmn.DictExportDTO;
import com.chen.medical.model.cmn.Dict;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 字典 服务实现类
 * </p>
 *
 * @author MaybeBin
 * @since 2023-05-16
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    @Override
    @Cacheable(value = "dict", keyGenerator = "keyGenerator")
    public List<Dict> findChildData(Long id) {
        LambdaQueryWrapper<Dict> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Dict::getParentId, id);
        List<Dict> dictList = baseMapper.selectList(wrapper);

        // 向list集合每个dict对象中设置hasChildren
        for (Dict dict:dictList) {
            Long dictId = dict.getId();
            boolean isChild = this.isChildren(dictId);
            dict.setHasChildren(isChild);
        }
        return dictList;
    }

    /**
     * 判断id下面是否有子节点
     * @param id 当前节点
     * @return true: 有；false: 没有
     */
    private boolean isChildren(Long id) {
        LambdaQueryWrapper<Dict> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Dict::getParentId, id);
        Integer count = baseMapper.selectCount(wrapper);
        return count > 0;
    }

    @Override
    public List<DictExportDTO> exportDictData() {
        List<Dict> dictList = baseMapper.selectList(null);
        List<DictExportDTO> exportList = new ArrayList<>();

        // 将DictDO转为DictExportDTO
        for (Dict dict : dictList) {
            DictExportDTO exportDTO = new DictExportDTO();
            BeanUtils.copyProperties(dict, exportDTO);
            exportList.add(exportDTO);
        }

        return exportList;
    }

    @Override
    public void batchImport(MultipartFile file){
        try {
            EasyExcel.read(file.getInputStream(), DictExportDTO.class, new DictListener(baseMapper)).sheet().doRead();
        } catch (IOException e) {
            throw new BusinessException(e.getMessage());
        }
    }

}
