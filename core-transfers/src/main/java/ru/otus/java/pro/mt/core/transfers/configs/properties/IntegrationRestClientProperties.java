package ru.otus.java.pro.mt.core.transfers.configs.properties;

import java.time.Duration;

public interface IntegrationRestClientProperties {
    String getUrl();
    Duration getReadTimeout();
    Duration getConnectTimeout();
}
