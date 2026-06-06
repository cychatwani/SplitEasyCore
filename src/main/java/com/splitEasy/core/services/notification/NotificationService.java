package com.splitEasy.core.services.notification;

import java.util.Map;

public interface NotificationService {

    void send(String type, String channel, String recipient, Map<String, String> variables);
}