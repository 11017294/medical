package com.chen.medical.cmn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * <p>
 *  cmn 启动类
 * </p>
 *
 * @author MaybeBin
 * @since 2023-05-16
 */
@SpringBootApplication
@ComponentScan("com.chen")
@EnableDiscoveryClient
public class ServiceCmnApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceCmnApplication.class, args);
    }

}
