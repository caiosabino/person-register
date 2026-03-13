package com.rm.person_register.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rm.person_register.mock.PersonMock;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.Mockito.mock;

class RabbitMqServiceTest {
    private final RabbitTemplate rabbitTemplate = mock(RabbitTemplate.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final RabbitMqService rabbitMqService = new RabbitMqService(rabbitTemplate, objectMapper);

    @Test
    void sendToQueueTest() throws JsonProcessingException {
        rabbitMqService.sendToQueue("teste", PersonMock.mock());
    }

    @Test
    void consumePersonRegisterTest() {
        rabbitMqService.consumePersonRegister("teste");
    }
}
