package com.chen.medical.hosp.controller;


import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.medical.hosp.service.HospitalSetService;
import com.chen.medical.model.hosp.HospitalSet;
import com.chen.medical.request.hosp.AddHospSetRequest;
import com.chen.medical.request.hosp.EditHospSetRequest;
import com.chen.medical.request.hosp.HospitalSetRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

/**
 * <p>
 * 医院设置表 前端控制器
 * </p>
 *
 * @author MaybeBin
 * @since 2023-05-11
 */
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
@Api(value = "医院设置接口", tags = {"医院设置接口"})
public class HospitalSetController {

    @Autowired
    private HospitalSetService hospitalSetService;

    @PostMapping("findPage/{current}/{limit}")
    @ApiOperation(value = "分页查询医院信息", response = Page.class)
    public Page<HospitalSet> findPage(@PathVariable long current,
                                      @PathVariable long limit,
                                      @RequestBody(required = false) HospitalSetRequest hospitalSetRequest){
        return hospitalSetService.findPage(current, limit, hospitalSetRequest);
    }

    @GetMapping("findAll")
    @ApiOperation(value = "获取所有医院信息", response = HospitalSet.class)
    public List<HospitalSet> findAllHospitalSet() {
        return hospitalSetService.list();
    }

    @GetMapping("getHospSet/{id}")
    @ApiOperation(value = "按id查询医院信息", response = HospitalSet.class)
    public HospitalSet getHospSet(@PathVariable Long id){
        return hospitalSetService.getById(id);
    }

    @PostMapping("add")
    @ApiOperation(value = "添加医院信息", response = HospitalSet.class)
    public Long addHospSet(@RequestBody AddHospSetRequest addHospSetRequest){
        HospitalSet hospitalSet = new HospitalSet();
        BeanUtils.copyProperties(addHospSetRequest, hospitalSet);

        Random random = new Random();
        String keyStr = String.valueOf(System.currentTimeMillis() + random.nextInt(1000));
        hospitalSet.setSignKey(SecureUtil.md5(keyStr));

        boolean flag = hospitalSetService.save(hospitalSet);

        if(!flag){
            new RuntimeException("添加医院信息失败！");
        }
        return hospitalSet.getId();
    }

    @PostMapping("editHospSet")
    @ApiOperation(value = "修改医院信息", response = HospitalSet.class)
    public Long editHospSet(@RequestBody EditHospSetRequest editHospSetRequest){
        HospitalSet hospitalSet = new HospitalSet();
        BeanUtils.copyProperties(editHospSetRequest, hospitalSet);
        boolean flag = hospitalSetService.updateById(hospitalSet);
        if(!flag){
            new RuntimeException("修改医院信息失败！");
        }
        return hospitalSet.getId();
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "删除医院信息", response = boolean.class)
    public boolean removeHospSet(@PathVariable Long id){
        return hospitalSetService.removeById(id);
    }

    @DeleteMapping("batchRemove")
    @ApiOperation(value = "批量删除医院信息", response = boolean.class)
    public boolean removeHospitalSet(@RequestBody List<Long> ids){
        return hospitalSetService.removeByIds(ids);
    }

    @PostMapping("lockHospSet/{id}/{status}")
    @ApiOperation(value = "设置医院信息状态", response = boolean.class)
    public Long lockHospSet(@PathVariable Long id,
                              @PathVariable Integer status){
        LambdaUpdateWrapper<HospitalSet> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(HospitalSet::getStatus, status);
        wrapper.eq(HospitalSet::getId, id);
        boolean flag = hospitalSetService.update(wrapper);

        if(!flag){
            new RuntimeException("设置医院信息状态失败！");
        }
        return id;
    }

    @PostMapping("sendKey/{id}")
    @ApiOperation(value = "发送签名密钥", response = boolean.class)
    public void sendKey(@PathVariable Long id){
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        String signKey = hospitalSet.getSignKey();
        String hoscode = hospitalSet.getHoscode();
        // TODO 发送短信
    }

}
