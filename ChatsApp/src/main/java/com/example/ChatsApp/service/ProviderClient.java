package com.example.notification.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ProviderClient {
    private static final Logger log = LoggerFactory.getLogger(ProviderClient.class);

    @CircuitBreaker(name = "externalProvider", fallbackMethod = "handleProviderFailure")
    public void sendToExternalApi(String type, String message) {
        log.info("Attempting to send {} via external provider...", type);

        // In a real app, you'd use RestTemplate or WebClient here
        // If the external API returns 500, throw an exception to trigger the Circuit Breaker
        if (Math.random() > 0.8) { // Simulating a 20% failure rate for testing
            throw new RuntimeException("External Service is currently unreachable");
        }

        log.info("{} sent successfully!", type);
    }

    // This method is called when the Circuit is OPEN or the provider fails
    public void handleProviderFailure(String type, String message, Throwable t) {
        log.error("Fallback logic triggered for {}. Reason: {}", type, t.getMessage());
        // Potential logic: Save to a 'retry' database table or a Dead Letter Queue (DLQ)
    }
}