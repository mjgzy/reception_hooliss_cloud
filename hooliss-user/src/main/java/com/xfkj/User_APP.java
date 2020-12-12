package com.xfkj;

import com.xfkj.utils.JWTUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.io.File;
import java.io.FileNotFoundException;

@SpringBootApplication
@EnableDiscoveryClient      //服务发现
@MapperScan("com.xfkj.mapper.user")
public class User_APP {
    public static void main(String[] args) {
//        JWTUtils.init();
        SpringApplication.run(User_APP.class,args);
    }
}
