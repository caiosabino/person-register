package com.rm.person_register.controller;

import com.rm.person_register.service.RabbitMqService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/rabbitmq")
public class RabbitMqController {

    @Value("${rabbitmq.queues.person-register}")
    private String personRegisterQueueName;

    private final RabbitMqService rabbitMqService;

    @PostMapping("/consume")
    public ResponseEntity<String> consumePersonRegisterMessage() {
        String message = rabbitMqService.consumeFromQueue(personRegisterQueueName);

        if (message == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(message);
    }

    @PostMapping("/peek")
    public ResponseEntity<String> peekPersonRegisterMessage() {
        String message = rabbitMqService.peekFromQueue(personRegisterQueueName);

        if (message == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(message);
    }
}
