package com.rm.person_register.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rm.person_register.data.repository.PersonRepository;
import com.rm.person_register.mock.PersonMock;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
class KafkaServiceTest {
    private final KafkaTemplate kafkaTemplate = mock(KafkaTemplate.class);
    private final Acknowledgment acknowledgment = mock(Acknowledgment.class);

    private KafkaService kafkaService = new KafkaService(kafkaTemplate);

    @Test
    public void senToTopicTest() throws JsonProcessingException {
        kafkaService.sendToTopic("teste", PersonMock.mock());
    }

    @Test
    public void consumePersonRegister(){
        kafkaService.consumePersonRegister("teste", acknowledgment);
    }
}
