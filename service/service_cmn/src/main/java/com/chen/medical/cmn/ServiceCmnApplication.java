package com.chen.medical.cmn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
public class ServiceCmnApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceCmnApplication.class, args);
    }

}
