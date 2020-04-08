package com.xfkj.config;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

//@Configuration
public class WebMvcRegistrationAdpater implements WebMvcRegistrations {

    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        System.err.println("执行了>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        return new MyRequestMappingHandlerMapping();
    }

    public class MyRequestMappingHandlerMapping extends RequestMappingHandlerMapping {


        @Override
        protected boolean isHandler(Class<?> beanType) {

            // 原来逻辑:
            /*
            return (AnnotatedElementUtils.hasAnnotation(beanType, Controller.class) ||
                    AnnotatedElementUtils.hasAnnotation(beanType, RequestMapping.class));
             */
            return super.isHandler(beanType) &&
                    !AnnotatedElementUtils.hasAnnotation(beanType, FeignClient.class);
        }
    }
}