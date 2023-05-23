package com.chen.medical.hosp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * <p>
 *  ServiceHosp启动类
 * </p>
 *
 * @author MaybeBin
 * @since 2023-05-10
 */
@SpringBootApplication
@ComponentScan("com.chen")
@EnableDiscoveryClient
public class ServiceHospApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceHospApplication.class, args);
    }

}
