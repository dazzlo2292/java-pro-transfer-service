package ru.otus.java.pro.mt.core.transfers.metrics;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ExampleMetricsService {
    private final Counter exampleMetricCounter;
    private final AtomicInteger customMetricGauge;

    public ExampleMetricsService(MeterRegistry meterRegistry) {
        exampleMetricCounter = Counter.builder("custom_metric_name")
                .description("Description of custom metric")
                .tags("environment", "development")
                .register(meterRegistry);

        customMetricGauge = meterRegistry.gauge("custom_gauge", new AtomicInteger(0));
    }
    public void incrementCustomMetric() {
        exampleMetricCounter.increment();
    }

    public void changeCustomGauge() {
        customMetricGauge.set((int)(Math.random() * 1000));
    }
}