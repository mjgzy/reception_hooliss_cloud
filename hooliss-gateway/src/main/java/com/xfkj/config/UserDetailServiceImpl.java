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
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private FeignUserService feignUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /**
         * 将我们的登录逻辑写在这里
         * 我是直接在这里写的死代码，其实应该从数据库中根据用户名去查
         */
        Wuser userByUsername = feignUserService.findUserByUsername(username);
        if (userByUsername == null) {
            //返回null时，后边就会抛出异常，就会登录失败。但这个异常并不需要我们处理
            return null;
        }

        //这是构造用户权限组的代码
        //但是这个权限上加了ROLE_前缀，而在之前的配置上却没有加。
        //与其说这不好理解，倒不如说这是他设计上的一个小缺陷
//        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
//        List<SimpleGrantedAuthority> list = new ArrayList<>();
//        list.add(authority);
        //这个user是UserDetails的一个实现类
        //用户密码实际是lyn4ever,前边加{noop}是不让SpringSecurity对密码进行加密，使用明文和输入的登录密码比较
        //如果不写{noop},它就会将表表单密码进行加密，然后和这个对比
//        User user = new User(userByUsername.getUName(), userByUsername.getUPwd(), list);
//        if (userByUsername.getUName().equals(username)) {
//            UserDetails user = User.withUsername(userByUsername.getUName())
//                    .password(userByUsername.getUPwd())
//                    .roles("USER")
//                    .build();
//            return user;
//        }

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        list.add(authority);
        return User.withUsername(userByUsername.getUName())
                .password(userByUsername.getUPwd())
                .roles("USER").authorities(list)
                .build();
//        if (userByUsername.getRole().equals("admin")) {
//            SimpleGrantedAuthority authority1 = new SimpleGrantedAuthority("ROLE_ADMIN");
//            list.add(authority1);
//            user.
//            return user;
//        }
//        //其他返回null
//        throw new UsernameNotFoundException("UserName " + username + " not found");
    }
//    @Override
//    public Mono<UserDetails> findByUsername(String username) {
//        /*定义权限集合*/
//        List<GrantedAuthority> authority = new ArrayList<>();
//        SimpleGrantedAuthority role_seller = new SimpleGrantedAuthority("ROLE_USER");
//        authority.add(role_seller);
//        if (username == null) {
//            return null;
//        }
//        Wuser customer = feignUserService.findUserByUsername(username);
//        if(customer != null){
//            if (customer.getUName().equals(username)) {
//                UserDetails user = User.withUsername(customer.getUName())
//                        .password(customer.getUPwd())
//                        .roles("USER")
//                        .build();
//                return Mono.just(user);
//            }
//        }
//        return Mono.error(new UsernameNotFoundException("User Not Found"));
//    }


}

