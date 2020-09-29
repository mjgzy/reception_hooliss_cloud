package com.xfkj.config;

import com.alibaba.fastjson.JSON;
import com.xfkj.enums.CommonEnum;
import com.xfkj.tools.ResultBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SuccessHandler appLoginInSuccessHandler;
    @Autowired
    private MyAuthenticationProvider myAuthenticationProvider;
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }
    /**
     * 配置用户权限组和接口路径的关系
     * 和一些其他配置
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()     // 对请求进行验证
                .antMatchers("/user/login").permitAll() //所有人都能访问用户登录接口
                .antMatchers("/index-provider/**","/es/**","/brand-provider/**","/watch-provider/**/*").permitAll()
                .antMatchers("/admin/**").hasRole("ROLE_ADMIN")     // 必须有ADMIN权限
                .antMatchers("/hoolissArea/**","/user-provider/**","/userAddress-provider/**").hasRole("ROLE_USER")     // 必须有ADMIN权限
//                .antMatchers("/user-provider/**").hasAnyRole("ROLE_USER", "ROLE_ADMIN")       //有任意一种权限
                .anyRequest()     //任意请求（这里主要指方法）
                .authenticated()   //// 需要身份认证
                .and()   //表示一个配置的结束
                .formLogin().loginProcessingUrl("/user/login").permitAll()  //开启SpringSecurity内置的表单登录，会提供一个/login接口
                .and()
                .logout().permitAll()  //开启SpringSecurity内置的退出登录，会为我们提供一个/logout接口
                .and()
                .csrf().disable().
                authenticationProvider(myAuthenticationProvider);    //关闭csrf跨站伪造请求
    }
    @Bean
    LoginFromFilter myAuthenticationFilter() throws Exception {
        LoginFromFilter filter = new LoginFromFilter();
        filter.setAuthenticationManager(authenticationManagerBean());
        filter.setAuthenticationSuccessHandler(appLoginInSuccessHandler);
        filter.setAuthenticationFailureHandler((httpServletRequest, httpServletResponse, e) -> {
            httpServletResponse.setContentType("application/json;charset=utf-8");
            httpServletResponse.getWriter().write(JSON.toJSONString(new ResultBody<>(CommonEnum.INTERNAL_SERVER_ERROR)));
        });
        return filter;
    }
}
