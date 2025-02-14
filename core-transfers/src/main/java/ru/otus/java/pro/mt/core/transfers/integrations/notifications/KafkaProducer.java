package ru.otus.java.pro.mt.core.transfers.integrations.notifications;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.java.pro.mt.core.transfers.dtos.KafkaTransferStatusDto;
import org.springframework.kafka.core.KafkaTemplate;

@Component
public class KafkaProducer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class.getName());

    private static final String MESSAGE_TOPIC = "mt.transfers.status.info";

    @Autowired
    private KafkaTemplate<String, KafkaTransferStatusDto> kafkaTemplate;

    public void send(KafkaTransferStatusDto message) {
        kafkaTemplate.send(MESSAGE_TOPIC, message);
        logger.info(message.toString());
    }
}