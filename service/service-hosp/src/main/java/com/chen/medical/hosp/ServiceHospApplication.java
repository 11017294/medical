package com.chen.medical.hosp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * <p>
 *  ServiceHosp启动类
 * </p>
 *
 * @author：MaybeBin
 * @Date: 2023-05-10 17:11
 */
@SpringBootApplication
@ComponentScan("com.chen")
public class ServiceHospApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceHospApplication.class, args);
    }

}
