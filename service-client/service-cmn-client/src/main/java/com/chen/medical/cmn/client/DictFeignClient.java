package com.chen.medical.cmn.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * <p>
 * dict Feign
 * </p>
 *
 * @author MaybeBin
 * @since 2023-05-23
 */
@Service
@FeignClient("service-cmn")
public interface DictFeignClient {

    @GetMapping("/admin/cmn/dict/getName/{dictCode}/{value}")
    String getDictName(@PathVariable("dictCode") String dictCode,
                       @PathVariable("value") String value);

    @GetMapping("/admin/cmn/dict/getName/{dictCode}")
    String getDictName(@PathVariable("dictCode") String dictCode);

}
