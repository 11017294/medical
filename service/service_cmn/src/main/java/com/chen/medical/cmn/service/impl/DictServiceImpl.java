package com.chen.medical.cmn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.medical.cmn.mapper.DictMapper;
import com.chen.medical.cmn.service.DictService;
import com.chen.medical.dto.cmn.DictExportDTO;
import com.chen.medical.model.cmn.Dict;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

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

}
