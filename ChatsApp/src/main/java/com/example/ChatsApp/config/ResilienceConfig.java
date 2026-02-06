package com.example.ChatsApp.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.core.registry.RegistryEventConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResilienceConfig {

    private static final Logger log = LoggerFactory.getLogger(ResilienceConfig.class);

    @Bean
    public RegistryEventConsumer<CircuitBreaker> myCircuitBreakerLog() {
        return new RegistryEventConsumer<>() {
            @Override
            public void onEntryAddedEvent(io.github.resilience4j.core.registry.EntryAddedEvent<CircuitBreaker> entryAddedEvent) {
                // Attach listener to every new Circuit Breaker created
                entryAddedEvent.getAddedEntry().getEventPublisher()
                        .onStateTransition(event -> log.info("CIRCUIT BREAKER ALERT: {} changed state from {} to {}",
                                event.getCircuitBreakerName(),
                                event.getStateTransition().getFromState(),
                                event.getStateTransition().getToState()));
            }

            @Override
            public void onEntryRemovedEvent(io.github.resilience4j.core.registry.EntryRemovedEvent<CircuitBreaker> entryRemovedEvent) {}

            @Override
            public void onEntryReplacedEvent(io.github.resilience4j.core.registry.EntryReplacedEvent<CircuitBreaker> entryReplacedEvent) {}
        };
    }
}