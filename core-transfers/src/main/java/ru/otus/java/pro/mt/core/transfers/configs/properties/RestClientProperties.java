package ru.otus.java.pro.mt.core.transfers.configs.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestClientProperties {
    private String url;
    private Duration readTimeout;
    private Duration connectTimeout;
}
