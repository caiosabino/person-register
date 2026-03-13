package com.rm.person_register.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rm.person_register.data.entity.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitMqService {
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    protected void sendToQueue(String queueName, Person person) throws JsonProcessingException {
        rabbitTemplate.convertAndSend(queueName, objectMapper.writeValueAsString(person));
    }

    @RabbitListener(
            queues = "${rabbitmq.queues.person-register}",
            autoStartup = "${rabbitmq.listener.person-register.enabled:true}"
    )
    public void consumePersonRegister(@Payload String payload) {
        log.info("Message consumed from queue: {}", payload);
    }
}
