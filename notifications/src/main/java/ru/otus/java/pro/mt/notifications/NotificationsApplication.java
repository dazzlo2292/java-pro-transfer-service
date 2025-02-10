package ru.otus.java.pro.mt.notifications;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.listener.ConsumerSeekAware;

@EnableKafka
@SpringBootApplication
public class NotificationsApplication implements ConsumerSeekAware {
    public static void main(String[] args) {
        SpringApplication.run(NotificationsApplication.class, args);
    }
}