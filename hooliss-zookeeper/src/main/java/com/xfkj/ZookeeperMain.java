package com.xfkj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient      //服务发现
public class ZookeeperMain {
    public static void main(String[] args) {
        SpringApplication.run(ZookeeperMain.class,args);
    }
}
