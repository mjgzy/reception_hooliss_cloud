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
@MapperScan("com.xfkj.mapper.commodity")
public class Commodity_Provider_APP {
    public static void main(String[] args) {
        try {
            File name = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX+"mybatis/mapper");
            System.err.println(name.getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.setProperty("es.set.netty.runtime.available.processors","false");SpringApplication.run(Commodity_Provider_APP.class, args);
        SpringApplication.run(Commodity_Provider_APP.class,args);

    }
}
