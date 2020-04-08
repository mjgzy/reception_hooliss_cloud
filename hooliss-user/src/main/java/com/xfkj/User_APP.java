package com.xfkj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.xfkj.mapper.user")
public class User_APP {
    public static void main(String[] args) {
//        try {
//            File name = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX+"mybatis/mapper");
//            System.err.println(name.getPath());
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        SpringApplication.run(User_APP.class,args);
    }
}
