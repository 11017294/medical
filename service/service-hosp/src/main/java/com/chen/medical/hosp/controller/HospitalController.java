package com.chen.medical.hosp.controller;

import com.chen.medical.hosp.service.HospitalService;
import com.chen.medical.model.hosp.Hospital;
import com.chen.medical.request.hosp.HospitalRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *
 * </p>
 *
 * @author MaybeBin
 * @since 2023-05-23
 */
@RestController
@RequestMapping("/admin/hosp/hospital")
@CrossOrigin
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @PostMapping("list/{page}/{limit}")
    public Page<Hospital> getHospitalPage(@PathVariable Integer page,
                                          @PathVariable Integer limit,
                                          HospitalRequest hospitalRequest) {
        return hospitalService.getHospitalPage(page, limit, hospitalRequest);
    }

}
