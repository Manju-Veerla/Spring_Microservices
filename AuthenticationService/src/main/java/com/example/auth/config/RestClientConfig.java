package com.example.auth.config;

import com.example.auth.client.UserServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
@RequiredArgsConstructor
public class RestClientConfig {

    @Value("${user.service.url}")
    private String userServiceUrl;


    @Bean
    public UserServiceClient userServiceClient() {
        RestClient restClient = RestClient.builder()
                .baseUrl(userServiceUrl)
                .defaultHeader("Content-Type", "application/json")
                .build();
        var restClientAdapter = RestClientAdapter.create(restClient);
        var httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
        return httpServiceProxyFactory.createClient(UserServiceClient.class);
    }

}
