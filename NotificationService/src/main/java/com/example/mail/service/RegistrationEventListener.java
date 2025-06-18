package com.example.mail.service;

import com.example.user.event.UserRegisteredEvent;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationEventListener {
    private NotificationService notificationService;

    @KafkaListener(topics = "user-registered", groupId = "notification-group")
    public void listen(UserRegisteredEvent userRegisteredEvent) {
        notificationService.sendRegistrationEmail(userRegisteredEvent);
    }
}
