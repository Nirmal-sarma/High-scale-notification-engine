package com.example.ChatsApp.service;

import com.example.ChatsApp.model.NotificationEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class NotificationService {
    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String TOPIC = "notification-topic";

    public NotificationService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void enqueueNotification(NotificationEvent event) {
        log.info("Enqueuing notification event for user: {}", event.getUserId());

        // We use userId as the Kafka Key to ensure ordering for that specific user
        kafkaTemplate.send(TOPIC, event.getUserId(), event)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Message sent to Kafka successfully");
                    } else {
                        log.error("Failed to send message to Kafka", ex);
                    }
                });
    }
}
