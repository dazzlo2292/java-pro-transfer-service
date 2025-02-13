package ru.otus.java.pro.mt.core.transfers.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import ru.otus.java.pro.mt.core.transfers.configs.properties.LimitsIntegrationRestClientProperties;
import ru.otus.java.pro.mt.core.transfers.factories.RestClientFactory;

@Configuration
@RequiredArgsConstructor
public class RestClientsConfig {
    private final LimitsIntegrationRestClientProperties limitsRestClientProperties;

    // @Bean
    public RestTemplate commonRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    @ConditionalOnMissingBean(RestTemplate.class)
    public RestClient limitsClient(RestClientFactory restClientFactory) {
        return restClientFactory.createRestClient(limitsRestClientProperties);
    }
}
