package com.xfkj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.xfkj.feign")
public class Consumer_Two_APP {
    public static void main(String[] args) {
        SpringApplication.run(Consumer_Two_APP.class,args);
    }
}
