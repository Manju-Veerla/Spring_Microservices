package com.example.mail.service;

import com.example.mail.event.UserRegisteredEvent;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationEventListener {
    private NotificationService notificationService;

    @KafkaListener(topics = "user-registered", groupId = "notification-service-group")
    public void listen(UserRegisteredEvent userRegisteredEvent) {
        notificationService.sendRegistrationEmail(userRegisteredEvent);
    }
}
