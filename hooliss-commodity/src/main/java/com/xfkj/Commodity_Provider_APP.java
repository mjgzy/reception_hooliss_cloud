package com.xfkj;

import com.alibaba.csp.sentinel.transport.config.TransportConfig;
import com.xfkj.utils.JWTUtils;
import org.jasypt.util.text.BasicTextEncryptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient  //服务发现
@MapperScan("com.xfkj.mapper.commodity")
public class Commodity_Provider_APP {

    public static void main(String[] args) {
        JWTUtils.init();
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        //加密所需的salt(盐),自定义
        textEncryptor.setPassword("retail_salt");
        String encrypt = textEncryptor.encrypt("jdbc:mysql://49.232.36.244:6033/t184watchitems?Unicode=true&characterEncoding=UTF-8&serverTimezone=UTC&zeroDateTimeBehavior=convertToNull");
        String wcnm = textEncryptor.encrypt("moujian");
        String qnmlgb = textEncryptor.encrypt("Qbkjmjlikeww,.");
        System.err.println(encrypt);
        System.err.println(wcnm);
        System.err.println(qnmlgb);
        SpringApplication.run(Commodity_Provider_APP.class,args);
    }
}
