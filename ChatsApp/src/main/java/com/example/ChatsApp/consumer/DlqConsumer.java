package com.example.ChatsApp.consumer;

import com.example.ChatsApp.model.NotificationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class DlqConsumer {
    private static final Logger log = LoggerFactory.getLogger(DlqConsumer.class);

    @KafkaListener(topics = "notification-topic.DLT", groupId = "dlq-monitoring-group")
    public void handleDeadLetters(NotificationEvent event) {
        log.error("🚨 ALERT: Message landed in Dead Letter Topic!");
        log.error("Failed Event ID: {} | User ID: {} | Content: {}",
                event.getEventId(), event.getUserId(), event.getMessage());

        // Here you could save to a 'failed_jobs' DB table for manual retry later
    }
}