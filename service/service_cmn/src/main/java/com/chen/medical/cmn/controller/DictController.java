package com.chen.medical.cmn.controller;


import com.chen.medical.cmn.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 组织架构表 前端控制器
 * </p>
 *
 * @author MaybeBin
 * @since 2023-05-16
 */
@Controller
@RequestMapping("/dict")
public class DictController {

    @Autowired
    private DictService dictService;

}
