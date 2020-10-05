package com.xfkj.provider;

import com.xfkj.service.SecurityUserDetailsService;
//import com.yaomy.security.oauth2.event.event.UserLoginFailedEvent;
//import com.yaomy.security.oauth2.exception.PasswordException;
//import com.yaomy.security.oauth2.exception.UsernameException;
//import com.yaomy.security.oauth2.service.UserAuthDetailsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Objects;

/**
 * @Description: 用户自定义身份认证
 * @ProjectName: spring-parent
 * @Package: com.yaomy.security.provider.MyAuthenticationProvider
 * @Date: 2019/7/2 17:17
 * @Version: 1.0
 */
@Component
public class UserAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private SecurityUserDetailsService authUserDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ApplicationEventPublisher publisher;
    /**
     * @Description 认证处理，返回一个Authentication的实现类则代表认证成功，返回null则代表认证失败
     * @Date 2019/7/5 15:19
     * @Version  1.0
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        if(StringUtils.isBlank(username)){
            throw new UsernameNotFoundException("username用户名不可以为空");
        }
        if(StringUtils.isBlank(password)){
            throw new BadCredentialsException("密码不可以为空");
        }
        //获取用户信息
        Mono<UserDetails> byUsername = authUserDetailsService.findByUsername(username);
        //比较前端传入的密码明文和数据库中加密的密码是否相等
        if (!passwordEncoder.matches(password, Objects.requireNonNull(byUsername.block()).getPassword())) {
            //发布密码不正确事件
//            publisher.publishEvent(new UserLoginFailedEvent(authentication));
            throw new BadCredentialsException("password密码不正确");
        }
        //获取用户权限信息
        Collection<? extends GrantedAuthority> authorities = Objects.requireNonNull(byUsername.block()).getAuthorities();
        return new UsernamePasswordAuthenticationToken(byUsername, password, authorities);

    }
    /**
     * @Description 如果该AuthenticationProvider支持传入的Authentication对象，则返回true
     * @Date 2019/7/5 15:18
     * @Version  1.0
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }

}
