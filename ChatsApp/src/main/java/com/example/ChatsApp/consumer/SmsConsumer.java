package com.example.ChatsApp.consumer;


import com.example.ChatsApp.model.NotificationEvent;
import com.example.notification.service.ProviderClient;
import com.example.ChatsApp.service.RateLimiterService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class SmsConsumer {
    private static final Logger log = LoggerFactory.getLogger(SmsConsumer.class);
    private final ProviderClient providerClient;
    private final RateLimiterService rateLimiter;

    public SmsConsumer(ProviderClient providerClient, RateLimiterService rateLimiter) {
        this.providerClient = providerClient;
        this.rateLimiter = rateLimiter;
    }

    @KafkaListener(topics = "notification-topic", groupId = "sms-group")
    public void consumeSms(NotificationEvent event) {
        // Logic check for SMS type
        if (!"SMS".equalsIgnoreCase(event.getType())) return;

        providerClient.sendToExternalApi("SMS", event.getMessage());
    }
}