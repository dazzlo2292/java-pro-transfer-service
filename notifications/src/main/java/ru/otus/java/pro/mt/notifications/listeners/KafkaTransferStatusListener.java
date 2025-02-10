package ru.otus.java.pro.mt.notifications.listeners;

import ru.otus.java.pro.mt.notifications.dtos.KafkaTransferStatusDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

@Component
public class KafkaTransferStatusListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaTransferStatusListener.class);

    private static final String CONSUMER_GROUP_ID = "my-consumer-group";

    private static final String MESSAGE_TOPIC = "mt.transfers.status.info";

    private static final String PARTITION = "0";
    private static final String INITIAL_OFFSET = "0";

    @KafkaListener(groupId = CONSUMER_GROUP_ID, topicPartitions = @TopicPartition(
            topic = MESSAGE_TOPIC, partitionOffsets = {@PartitionOffset(
            partition = PARTITION,
            initialOffset = INITIAL_OFFSET)}))
    void consumeMessage(KafkaTransferStatusDto message) {
        LOGGER.info("По переводу {} клиенту отправлена нотификация", message.getTransferId());
    }
}
