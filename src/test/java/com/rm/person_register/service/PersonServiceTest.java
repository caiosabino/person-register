package com.rm.person_register.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rm.person_register.data.repository.PersonRepository;
import com.rm.person_register.mock.PersonMock;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
class PersonServiceTest {
    private final PersonRepository personRepository = mock(PersonRepository.class);
    private final KafkaService kafkaService = mock(KafkaService.class);

    private PersonService personService = new PersonService(personRepository, kafkaService);

    @Test
    public void addPersonInDbTest() throws JsonProcessingException {
        personService.addPersonInDb(PersonMock.mock());
    }

    @Test
    public void updatePersonInDbTest() throws JsonProcessingException {
        personService.updatePersonInDb(PersonMock.mock());
    }

    @Test
    public void findAll() {
        personService.findAll(PageRequest.of(0, 10));
    }

    @Test
    public void findById() {
        personService.findById(1L);
    }

    @Test
    public void findByDocument() {
        personService.findByDocument(1L);
    }

    @Test
    public void delete() {
        personService.delete(PersonMock.mock());
    }
}
