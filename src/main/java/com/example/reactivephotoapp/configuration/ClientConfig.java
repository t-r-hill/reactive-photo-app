package com.example.reactivephotoapp.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;

@Configuration
public class ClientConfig {

    @Value("${unsplash.search.uri}")
    private URI unsplashSearchUri;

    @Value("${unsplash.client-id}")
    private String unsplashSecret;

    @Value("${pexels.search.uri}")
    private URI pexelsSearchUri;

    @Value("${pexels.client-id}")
    private String pexelsSecret;

    @Bean
    public WebClient unsplashClient() {
        return WebClient.builder()
                .baseUrl(unsplashSearchUri.toString())
                .defaultHeader(HttpHeaders.AUTHORIZATION, unsplashSecret)
                .build();
    }

    @Bean
    public WebClient pexelsClient() {
        return WebClient.builder()
                .baseUrl(pexelsSearchUri.toString())
                .defaultHeader(HttpHeaders.AUTHORIZATION, pexelsSecret)
                .build();
    }
}
