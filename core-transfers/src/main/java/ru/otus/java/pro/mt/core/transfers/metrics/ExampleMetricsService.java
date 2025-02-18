package ru.otus.java.pro.mt.core.transfers.metrics;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ExampleMetricsService {
    private final Counter exampleMetricCounter;
    private final AtomicInteger exampleMetricGauge;

    public ExampleMetricsService(MeterRegistry meterRegistry) {
        exampleMetricCounter = Counter.builder("custom_metric_name")
                .description("Description of custom metric")
                .tags("environment", "development")
                .register(meterRegistry);

        exampleMetricGauge = meterRegistry.gauge("custom_gauge", new AtomicInteger(0));
    }
    public void incrementExampleMetric() {
        exampleMetricCounter.increment();
    }

    public void changeExampleGauge() {
        exampleMetricGauge.set((int)(Math.random() * 1000));
    }
}