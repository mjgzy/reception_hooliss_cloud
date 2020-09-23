package com.xfkj;

import com.xfkj.utils.JWTUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.xfkj.feign.commodity")
@MapperScan("com.xfkj.mapper.order")
public class Order_APP {

    public static void main(String[] args) {
        JWTUtils.init();SpringApplication.run(Order_APP.class,args);
    }
}
