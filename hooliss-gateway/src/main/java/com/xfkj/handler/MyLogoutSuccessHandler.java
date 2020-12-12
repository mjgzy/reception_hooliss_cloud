package com.xfkj.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.ServerLogoutHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class MyLogoutSuccessHandler implements ServerLogoutHandler {

    @Override
    public Mono<Void> logout(WebFilterExchange webFilterExchange, Authentication authentication) {
        System.err.println("登出.......");
        return null;
    }
//    protected void configure(ServerHttpSecurity http) {
//        ServerLogoutHandler logoutHandler = this.createLogoutHandler();
//        if (logoutHandler != null) {
//            this.logoutWebFilter.setLogoutHandler(logoutHandler);
//        }
//
//        http.addFilterAt(this.logoutWebFilter, SecurityWebFiltersOrder.LOGOUT);
//    }
}
