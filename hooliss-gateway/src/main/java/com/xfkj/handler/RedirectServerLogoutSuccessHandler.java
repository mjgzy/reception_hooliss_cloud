package com.xfkj.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.DefaultServerRedirectStrategy;
import org.springframework.security.web.server.ServerRedirectStrategy;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;

import java.net.URI;

public class RedirectServerLogoutSuccessHandler implements ServerLogoutSuccessHandler {
    public static final String DEFAULT_LOGOUT_SUCCESS_URL = "/auth/logout";
    private URI logoutSuccessUrl = URI.create("/auth/logout");
    private ServerRedirectStrategy redirectStrategy = new DefaultServerRedirectStrategy();

    public RedirectServerLogoutSuccessHandler() {
    }

    @Override
    public Mono<Void> onLogoutSuccess(WebFilterExchange exchange, Authentication authentication) {
        return this.redirectStrategy.sendRedirect(exchange.getExchange(), this.logoutSuccessUrl);
    }

    public void setLogoutSuccessUrl(URI logoutSuccessUrl) {
        Assert.notNull(logoutSuccessUrl, "logoutSuccessUrl cannot be null");
        this.logoutSuccessUrl = logoutSuccessUrl;
    }

}
