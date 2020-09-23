package com.xfkj.config;


import com.xfkj.entity.user.Wuser;
import com.xfkj.feign.user.FeignUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDetailServiceImpl implements ReactiveUserDetailsService {

    @Autowired
    private FeignUserService feignUserService;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        /*定义权限集合*/
        List<GrantedAuthority> authority = new ArrayList<>();
        SimpleGrantedAuthority role_seller = new SimpleGrantedAuthority("ROLE_USER");
        authority.add(role_seller);
        if (username == null) {
            return null;
        }
        Wuser customer = feignUserService.findUserByUsername(username);
        if(customer != null){
            if (customer.getUName().equals(username)) {
                UserDetails user = User.withUsername(customer.getUName())
                        .password(customer.getUPwd())
                        .roles("USER")
                        .build();
                return Mono.just(user);
            }
        }
        return Mono.error(new UsernameNotFoundException("User Not Found"));
    }


}

