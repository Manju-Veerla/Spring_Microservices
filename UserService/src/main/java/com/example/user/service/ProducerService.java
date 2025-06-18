package com.example.user.service;

import com.example.user.event.UserRegisteredEvent;
import com.example.user.model.response.UserDeptResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ProducerService {
    private KafkaTemplate<String, UserRegisteredEvent> kafkaTemplate;

    public void sendRegistrationEmail(UserDeptResponse userDeptResponse) {
        UserRegisteredEvent userRegisteredEvent = new UserRegisteredEvent(
                userDeptResponse.username(),
                userDeptResponse.firstName(),
                userDeptResponse.lastName(),
                userDeptResponse.email(),
                userDeptResponse.department().departmentName()
        );
        log.info("Start - Sending UserRegisteredEvent {} to Kafka topic user-registered", userRegisteredEvent);
        kafkaTemplate.send("user-registered", userRegisteredEvent);
        log.info("End - Sending UserRegisteredEvent {} to Kafka topic user-registered", userRegisteredEvent);

    }

}
