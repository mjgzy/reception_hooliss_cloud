package com.xfkj.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
public class ElasticSearchConfig {
    @PostConstruct
    void init() {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }

//    @Bean(name = "client")
//    public TransportClient transportClient() throws UnknownHostException {
//        TransportClient transportClient = new PreBuiltTransportClient(Settings.builder()
//        .put("cluster.name","my-application")
//        .put("xpack.security.user","elastic:mj296233")
//        .build())
//        .addTransportAddress(new TransportAddress(InetAddress.getByName("111.229.128.116"),9300)
//        );
//    return transportClient;
//    }
}
