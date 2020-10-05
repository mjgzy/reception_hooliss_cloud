package com.xfkj.config;

import com.xfkj.handler.AuthenticationFaillHandler;
import com.xfkj.handler.AuthenticationSuccessHandler;
import com.xfkj.handler.CustomHttpBasicServerAuthenticationEntryPoint;
import com.xfkj.provider.UserAuthenticationProvider;
import com.xfkj.service.SecurityUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SecurityConfig {

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private AuthenticationFaillHandler authenticationFaillHandler;
    @Autowired
    private CustomHttpBasicServerAuthenticationEntryPoint customHttpBasicServerAuthenticationEntryPoint;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserAuthenticationProvider userAuthenticationProvider;
    @Autowired
    private SecurityUserDetailsService securityUserDetailsService;
    //security的鉴权排除的url列表
    private static final String[] excludedAuthPages = {
            "/index-provider/**","/es/**","/brand-provider/**","/watch-provider/**/*",
            "/auth/logout",
            "/health",
            "/api/socket/**"
    };

    @Bean
    SecurityWebFilterChain webFluxSecurityFilterChain(ServerHttpSecurity http) throws Exception {
        http
                .authorizeExchange()
                .pathMatchers(excludedAuthPages).permitAll()  //无需进行权限过滤的请求路径
                .pathMatchers(HttpMethod.OPTIONS).permitAll() //option 请求默认放行
                .anyExchange().authenticated()
                .and()
                .httpBasic()
                .and()
                .formLogin().loginPage("/auth/login")
                .authenticationSuccessHandler(authenticationSuccessHandler) //认证成功
                .authenticationFailureHandler(authenticationFaillHandler) //登陆验证失败
                .and().exceptionHandling().authenticationEntryPoint(customHttpBasicServerAuthenticationEntryPoint)  //基于http的接口请求鉴权失败
                .and() .csrf().disable()//必须支持跨域
                .logout().disable();

        return http.build();
    }

    public static void main(String[] args) {
        System.err.println(new BCryptPasswordEncoder().encode("hooliss-123456"));
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
//        return  NoOpPasswordEncoder.getInstance(); //默认不加密
        return new BCryptPasswordEncoder(14); //指定4-31位的长度
    }

}
