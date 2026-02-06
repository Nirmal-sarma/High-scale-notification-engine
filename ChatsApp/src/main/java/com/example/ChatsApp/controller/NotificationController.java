package com.example.ChatsApp.controller;

import com.example.ChatsApp.model.NotificationEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public NotificationController(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/send")
    public ResponseEntity<String> triggerNotification(@RequestBody NotificationEvent event) {
        // Assign a unique ID if not present
        if (event.getEventId() == null) {
            event.setEventId(UUID.randomUUID().toString());
        }

        // 1. Publish to Kafka (The "Fan-out" start point)
        // We use userId as the 'key' to ensure all messages for one user
        // go to the same Kafka partition (maintains order).
        kafkaTemplate.send("notification-topic", event.getUserId(), event);

        return ResponseEntity.ok("Notification accepted and queued. ID: " + event.getEventId());
    }
}
