package com.xfkj.service;

import com.xfkj.entity.Wuser;
import com.xfkj.utils.MD5Encoder;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
public class SecurityUserDetailsService implements ReactiveUserDetailsService {


    @Autowired
    private FeignUserService feignUserService;
    @Override
    public Mono<UserDetails> findByUsername(String username) {
        //todo 预留调用数据库根据用户名获取用户
        Wuser userByUsername = feignUserService.findUserByUsername(username);
        if(!ObjectUtils.isEmpty(userByUsername)&&
                !StringUtils.isEmpty(userByUsername.getRole()) &&
                StringUtils.equals(userByUsername.getUName(),username)){
            List<GrantedAuthority> authn = new ArrayList<>();
            authn.add(new SimpleGrantedAuthority(userByUsername.getRole()));    //用户角色
//            User.withUsername(userByUsername.getUName())
//                    .password(userByUsername.getUPwd())
//                    .roles(userByUsername.getRole()).authorities(AuthorityUtils.commaSeparatedStringToAuthorityList(userByUsername.getRole()))
//                    .build();
            userByUsername.setAuths(authn);
            return Mono.just(userByUsername);
        }
        else{
            return Mono.error(new UsernameNotFoundException("User Not Found"));

        }

    }



}
