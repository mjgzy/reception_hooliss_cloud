package com.xfkj.config;

import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Autowired
    private MyInterceptor myInterceptor;

    @Autowired
    private UserArgumentResolver userArgumentResolver;
    @Override
    protected void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("redirect:/index/doIndex.xf");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    //    @Override
//    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
//        FastJsonConfig fastJsonConfig = new FastJsonConfig();
//        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteMapNullValue,
//                SerializerFeature.QuoteFieldNames, SerializerFeature.DisableCircularReferenceDetect);
//        fastConverter.setFastJsonConfig(fastJsonConfig);
//
//        List<MediaType> fastMediaTypes = new ArrayList<>();
//        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
//        fastMediaTypes.add(MediaType.parseMediaType(MediaType.TEXT_HTML_VALUE + ";charset=ISO-8859-1"));
//        fastConverter.setSupportedMediaTypes(fastMediaTypes);
//        converters.add(fastConverter);
//    }

    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(userArgumentResolver);
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myInterceptor).excludePathPatterns("/**").addPathPatterns("/order/**")
                //需要配置2：----------- 告知拦截器：/mystatic/admin/** 与 /mystatic/user/** 不需要拦截 （配置的是 路径）
                .excludePathPatterns("/mystatic/**", "/mystatic/","/**/*.js","/**/*.css");
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/mystatic/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/hystrix/**").addResourceLocations("classpath:/static/hystrix/");
    }
}
