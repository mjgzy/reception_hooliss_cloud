package com.xfkj.config;

import com.xfkj.annotations.UserAttribute;
import com.xfkj.tools.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@Configuration
public class UserArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        //判断是否有UserAttribute注解
        if(parameter.hasParameterAnnotation(UserAttribute.class)){
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Object object = webRequest.getAttribute(Constants.USER_SESSION,NativeWebRequest.SCOPE_SESSION); //从session获取user对象
        log.info("用户对象:"+object);
        return object;
    }
}
