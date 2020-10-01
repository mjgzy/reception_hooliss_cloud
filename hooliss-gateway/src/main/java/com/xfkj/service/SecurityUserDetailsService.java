package com.xfkj.service;

import com.xfkj.entity.Wuser;
import com.xfkj.utils.MD5Encoder;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class SecurityUserDetailsService implements ReactiveUserDetailsService {


    @Autowired
    private FeignUserService feignUserService;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        //todo 预留调用数据库根据用户名获取用户
        Wuser userByUsername = feignUserService.findUserByUsername(username);
        if(StringUtils.equals(userByUsername.getUName(),username)){
            UserDetails user = User.withUsername(userByUsername.getUName())
                    .password(MD5Encoder.encode(userByUsername.getUPwd(),username))
                    .roles(userByUsername.getRole()).authorities(AuthorityUtils.commaSeparatedStringToAuthorityList(userByUsername.getRole()))
                    .build();
            return Mono.just(user);
        }
        else{
            return Mono.error(new UsernameNotFoundException("User Not Found"));

        }

    }



}
