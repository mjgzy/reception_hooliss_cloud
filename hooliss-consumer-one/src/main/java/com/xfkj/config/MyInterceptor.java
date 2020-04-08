package com.xfkj.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.xfkj.exceptionHandling.XFException;
import com.xfkj.feign.user.FeignUserController;
import com.xfkj.pojo.user.Wuser;
import com.xfkj.tools.Constants;
import com.xfkj.tools.ResultBody;
import com.xfkj.utils.JWTUtils;
import com.xfkj.annotations.PassToken;
import com.xfkj.annotations.UserLoginToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class MyInterceptor implements HandlerInterceptor {
    @Autowired
    private FeignUserController feignUserController;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.err.println("本次拦截的请求:"+request.getAttribute("org.springframework.web.servlet.HandlerMapping.pathWithinHandlerMapping"));
        String token = String.valueOf(request.getSession().getAttribute(JWTUtils.TOKEN_HEADER));// 从 http 请求头中取出 token
        System.err.println("'来自拦截器的token:"+token);
        if(request.getSession().getAttribute(Constants.USER_SESSION)==null){
            return false;
        }
        // 如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }


        HandlerMethod handlerMethod=(HandlerMethod)handler;
        Method method=handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                if (token==null||token.equals("null")){
                    throw new XFException("401","您还未登陆,请登录" );
                }
                // 获取 token 中的 user id
                Integer userId;
                try {
                    userId = Integer.valueOf(JWT.decode(token).getAudience().get(0));
                    System.err.println("用户id:"+userId);
                } catch (JWTDecodeException j) {
                    throw new XFException("401",j.getMessage());
                }
                ResultBody resultBody=feignUserController.findById(userId);
                Wuser user=null;
                if(resultBody.getCode().equals("200")){
                    user = (Wuser) resultBody.getResult();
                    if (user == null) {
                        throw new XFException("用户不存在，请重新登录");
                    }
                }else{
                    throw new XFException("系统异常,请稍后再试!");
                }
                // 验证 token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getU_pwd())).build();
                try {
                    jwtVerifier.verify(token);
                } catch (JWTVerificationException e) {
                    throw new XFException("401",e.getMessage());
                }
                return true;
            }
        }
        return true;
    }
}
