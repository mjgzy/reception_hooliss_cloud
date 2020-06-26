package com.xfkj.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

//@Configuration
//@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 86400*30)
public class SessionConfig{

    @PostConstruct
    public static void haha(){
        System.err.println("@Postcontruct’在依赖注入完成后自动调用");
    }

    public void afterPropertiesSet() throws Exception {
        System.err.println("大大滴哇无");
        System.setProperty("jasypt.encryptor.password", "retail_salt");
    }
}
