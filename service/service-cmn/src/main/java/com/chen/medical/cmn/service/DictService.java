package com.chen.medical.cmn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.medical.dto.cmn.DictExportDTO;
import com.chen.medical.model.cmn.Dict;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    /**
     * 查找子列表
     * @param id
     * @return
     */
    List<Dict> findChildData(Long id);

    /**
     * 导出excel
     * @return
     */
    List<DictExportDTO> exportDictData();

    /**
     * 批量导入
     * @param file
     * @throws IOException
     */
    void batchImport(MultipartFile file);

    /**
     * 获取 dictName
     * @param dictCode
     * @param value
     * @return
     */
    String getDictName(String dictCode, String value);

    /**
     * 获取 dictName
     * @param dictCode
     * @return
     */
    String getDictName(String dictCode);

    /**
     * 根据 dictCode 获取子集
     * @param dictCode
     */
    List<Dict> findByDictCode(String dictCode);
}
