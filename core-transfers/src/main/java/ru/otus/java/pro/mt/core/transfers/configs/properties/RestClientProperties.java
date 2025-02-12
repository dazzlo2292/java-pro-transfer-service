package ru.otus.java.pro.mt.core.transfers.configs.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@NoArgsConstructor
@Data
@ConfigurationProperties("integrations.rest-client")
public class RestClientProperties {
    private String url;
    private Duration readTimeout;
    private Duration writeTimeout;
}
