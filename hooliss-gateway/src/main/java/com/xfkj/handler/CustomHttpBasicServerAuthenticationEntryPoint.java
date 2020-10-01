package com.xfkj.handler;

import com.google.gson.JsonObject;
import com.xfkj.enums.CommonEnum;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.authentication.HttpBasicServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 认证成功后访问新的接口需在请求头中添加基于httpbasic的认证鉴权信息，
 * 服务端收到请求后通过识别为httpbasic的鉴权信息，
 * 通过ServerHttpBasicAuthenticationConverter提取用户名和密码后进行鉴权，
 * 鉴权通过放行请求。
 *   此处自定义鉴权失败时的处理逻辑CustomHttpBasicServerAuthenticationEntryPoint，
 * 只需继承默认的httpbasic鉴权失败处理器HttpBasicServerAuthenticationEntryPoint
 * 并覆盖其commence方法即可:
 */
@Component
public class CustomHttpBasicServerAuthenticationEntryPoint extends HttpBasicServerAuthenticationEntryPoint{


    private static final String WWW_AUTHENTICATE = "WWW-Authenticate";
    private static final String DEFAULT_REALM = "Realm";
    private static String WWW_AUTHENTICATE_FORMAT = "Basic realm=\"%s\"";
    private String headerValue = createHeaderValue("Realm");
    public CustomHttpBasicServerAuthenticationEntryPoint() {
    }



    public void setRealm(String realm) {
        this.headerValue = createHeaderValue(realm);
    }

    private static String createHeaderValue(String realm) {
        Assert.notNull(realm, "realm cannot be null");
        return String.format(WWW_AUTHENTICATE_FORMAT, new Object[]{realm});
    }


    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add("Content-Type", "application/json; charset=UTF-8");
        response.getHeaders().set(HttpHeaders.AUTHORIZATION, this.headerValue);
        JsonObject result = new JsonObject();
        result.addProperty("status", CommonEnum.INTERNAL_SERVER_ERROR.getResultCode());
        result.addProperty("message", CommonEnum.INTERNAL_SERVER_ERROR.getResultCode());
        byte[] dataBytes=result.toString().getBytes();
        DataBuffer bodyDataBuffer = response.bufferFactory().wrap(dataBytes);
        return response.writeWith(Mono.just(bodyDataBuffer));
    }
}
