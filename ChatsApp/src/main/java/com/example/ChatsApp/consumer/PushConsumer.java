package com.example.ChatsApp.consumer;

import com.example.ChatsApp.model.NotificationEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class PushConsumer {
    private static final Logger log = LoggerFactory.getLogger(PushConsumer.class);
    private final SimpMessagingTemplate messagingTemplate;

    public PushConsumer(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @KafkaListener(topics = "notification-topic", groupId = "push-group")
    public void consumePush(NotificationEvent event) {
        if ("PUSH".equalsIgnoreCase(event.getType()) || "ALL".equalsIgnoreCase(event.getType())) {
            log.info("Pushing WebSocket notification to user: {}", event.getUserId());

            // This sends the message to /user/{userId}/queue/notifications
            messagingTemplate.convertAndSendToUser(
                    event.getUserId(),
                    "/queue/notifications",
                    event.getMessage()
            );
        }
    }
}