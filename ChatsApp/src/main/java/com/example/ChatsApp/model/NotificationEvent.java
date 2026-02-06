package com.example.ChatsApp.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationEvent {
    private String eventId;
    private String userId;
    private String message;
    private String type; // e.g., "SMS", "PUSH", "EMAIL"
    private String priority; // "HIGH", "LOW"
}
