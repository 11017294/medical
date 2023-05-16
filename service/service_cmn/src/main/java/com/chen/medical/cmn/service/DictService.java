package com.chen.medical.cmn.service;

import com.chen.medical.model.cmn.Dict;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 字典 服务类
 * </p>
 *
 * @author MaybeBin
 * @since 2023-05-16
 */
public interface DictService extends IService<Dict> {

    List<Dict> findChildData(Long id);

}
