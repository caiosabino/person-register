package com.rm.person_register.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rm.person_register.data.entity.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaService {
    private final KafkaTemplate template;

    protected void sendToTopic(String topicName, Person person) throws JsonProcessingException {
        template.send(topicName, new ObjectMapper().writeValueAsString(person));
    }

    @KafkaListener(topics = "person-register-topic")
    public void consumePersonRegister(@Payload String valor, Acknowledgment ack) {

        log.info("message sent to topic: {}", valor);

        // Commmit manual, que também será síncrono
        ack.acknowledge();
    }
}
