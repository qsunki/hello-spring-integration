package com.example.hellospringintegration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.http.dsl.Http;

@Configuration
public class HttpConfig {
    @Bean
    public IntegrationFlow inbound() {
        return IntegrationFlow.from(
                        Http.inboundGateway("/foo")
                                .requestMapping(m -> m.methods(HttpMethod.GET))
                                .requestPayloadType(String.class))
                .channel("httpRequest")
                .get();
    }

    @Bean
    public IntegrationFlow outbound() {
        return IntegrationFlow.from("httpOutRequest")
                .handle(
                        Http.outboundGateway("http://localhost:8080/foo")
                                .httpMethod(HttpMethod.GET)
                                .expectedResponseType(String.class))
                .get();
    }
}
