package com.xfkj.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.xfkj.entity.Wuser;
import com.xfkj.enums.CommonEnum;
import com.xfkj.utils.ResultBody;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.WebFilterChainServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Base64;

@Component
public class AuthenticationSuccessHandler extends WebFilterChainServerAuthenticationSuccessHandler {

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        ServerWebExchange exchange = webFilterExchange.getExchange();
        ServerHttpResponse response = exchange.getResponse();
        //设置headers
        HttpHeaders httpHeaders = response.getHeaders();
        httpHeaders.add("Content-Type", "application/json; charset=UTF-8");
        httpHeaders.add("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
        //设置body
        ResultBody<Wuser> wsResponse = new ResultBody<>(CommonEnum.SUCCESS);
        byte[] dataBytes = {};
        ObjectMapper mapper = new ObjectMapper();
        try {
            Wuser userDetails = (Wuser) authentication.getPrincipal();  //获取userDetails返回的用户对象
            byte[] authorization = (userDetails.getUsername() + ":" + userDetails.getPassword()).getBytes();
            String token = Base64.getEncoder().encodeToString(authorization);       //生成token
            httpHeaders.add(HttpHeaders.AUTHORIZATION, token);
            wsResponse.setData(userDetails);
            dataBytes = mapper.writeValueAsBytes(wsResponse);
        } catch (Exception ex) {
            ex.printStackTrace();
            JsonObject result = new JsonObject();
            result.addProperty("status", CommonEnum.INTERNAL_SERVER_ERROR.getResultCode());
            result.addProperty("message", "授权异常");
            dataBytes = result.toString().getBytes();
        }
        DataBuffer wrap = response.bufferFactory().wrap(dataBytes);
        return response.writeWith(Mono.just(wrap));
    }
}
