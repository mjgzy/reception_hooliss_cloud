package com.xfkj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.xfkj.feign.commodity")
@MapperScan("com.xfkj.mapper.order")
public class Order_APP {
    public static void main(String[] args) {
        SpringApplication.run(Order_APP.class,args);
    }
}
