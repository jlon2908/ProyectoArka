package com.arka.supplier_service.infraestructure.adapters.driver.reactive.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
public class WebClientConfig {

    @Bean
    public WebClient catalogWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl("http://localhost:8080")
                .build();
    }
}