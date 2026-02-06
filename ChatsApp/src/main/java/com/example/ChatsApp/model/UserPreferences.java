package com.example.ChatsApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPreferences {
    private String userId;
    private List<String> enabledChannels; // e.g., ["SMS", "PUSH"]
    private String preferredLanguage;    // e.g., "en", "es"
    private boolean dndEnabled;           // Do Not Disturb
}
