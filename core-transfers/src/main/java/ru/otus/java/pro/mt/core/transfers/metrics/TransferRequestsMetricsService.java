package ru.otus.java.pro.mt.core.transfers.metrics;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class TransferRequestsMetricsService {
    private final Counter transferRequestsMetricCounter;
    private final Counter successTransferRequestsMetricCounter;
    private final Counter failedTransferRequestsMetricCounter;

    public TransferRequestsMetricsService(MeterRegistry meterRegistry) {
        transferRequestsMetricCounter = Counter.builder("transfer-requests-metric")
                .description("Общее количество запросов на переводы")
                .tags("environment", "development")
                .register(meterRegistry);

        successTransferRequestsMetricCounter = Counter.builder("success-transfer-requests-metric")
                .description("Количество успешных запросов на переводы")
                .tags("environment", "development")
                .register(meterRegistry);

        failedTransferRequestsMetricCounter = Counter.builder("failed-transfer-requests-metric")
                .description("Количество запросов на переводы с ошибкой")
                .tags("environment", "development")
                .register(meterRegistry);
    }

    public void incrementTransferRequestsMetric() {
        transferRequestsMetricCounter.increment();
    }
    public void incrementSuccessTransferRequestsMetric() { successTransferRequestsMetricCounter.increment(); }
    public void incrementFailedTransferRequestsMetric() { failedTransferRequestsMetricCounter.increment(); }
}