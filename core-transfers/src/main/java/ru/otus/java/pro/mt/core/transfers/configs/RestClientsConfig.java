package ru.otus.java.pro.mt.core.transfers.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import ru.otus.java.pro.mt.core.transfers.configs.properties.LimitsIntegrationProperties;
import ru.otus.java.pro.mt.core.transfers.factories.RestClientFactory;

@Configuration
@RequiredArgsConstructor
public class RestClientsConfig {
    private final LimitsIntegrationProperties limitsRestClientProperties;

    @Bean
    public RestClient limitsClient(RestClientFactory restClientFactory) {
        return restClientFactory.createRestClient(limitsRestClientProperties.getRestClient());
    }
}
