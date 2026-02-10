package com.example.ChatsApp.consumer;

import com.example.ChatsApp.model.NotificationEvent;
import com.example.ChatsApp.service.ProviderClient;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EmailConsumer {
    private final ProviderClient providerClient;

    public EmailConsumer(ProviderClient providerClient) {
        this.providerClient = providerClient;
    }

    @KafkaListener(topics = "notification-topic", groupId = "email-group")
    public void consumeEmail(NotificationEvent event) {
        if ("EMAIL".equalsIgnoreCase(event.getType()) || "ALL".equalsIgnoreCase(event.getType())) {
            // Logic for Email delivery
            providerClient.sendToExternalApi("EMAIL", event.getMessage());
        }
    }
}
