package com.example.user.config;

import com.example.user.client.DepartmentClient;
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

    @Value("${department.service.url}")
    private String departmentServiceUrl;


    @Bean
    public DepartmentClient departmentClient() {
        RestClient restClient = RestClient.builder()
                .baseUrl(departmentServiceUrl)
                .defaultHeader("Content-Type", "application/json")
                .build();
        var restClientAdapter = RestClientAdapter.create(restClient);
        var httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
        return httpServiceProxyFactory.createClient(DepartmentClient.class);
    }

}
