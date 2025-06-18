package com.example.mail.service;

import com.example.mail.event.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final JavaMailSender javaMailSender;

    @KafkaListener(topics = "user-registered")
    public void sendRegistrationEmail(UserRegisteredEvent userRegisteredEvent){
        log.info("Got Message from user-registered topic {}", userRegisteredEvent);
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("notification@email.com");
            messageHelper.setTo(userRegisteredEvent.email());
            messageHelper.setSubject(String.format("You with username %s is registered successfully", userRegisteredEvent.username()));
            messageHelper.setText(String.format("""
                            Hi %s,%s

                            User with name registered successfully.
                            
                            Username: %s
                            Department: %s
                            
                            Best Regards
                            Admin
                            """,
                    userRegisteredEvent.firstName(),
                    userRegisteredEvent.lastName(),
                    userRegisteredEvent.username(),
                    userRegisteredEvent.departmentName()));
        };
        try {
            javaMailSender.send(messagePreparator);
            log.info("User registration email sent!!");
        } catch (MailException e) {
            log.error("Exception occurred when sending mail", e);
            throw new RuntimeException("Exception occurred when sending mail ", e);
        }
    }
}
