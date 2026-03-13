package com.rm.person_register.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rm.person_register.data.repository.PersonRepository;
import com.rm.person_register.mock.PersonMock;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;

import static org.mockito.Mockito.mock;

class PersonServiceTest {
    private final PersonRepository personRepository = mock(PersonRepository.class);
    private final RabbitMqService rabbitMqService = mock(RabbitMqService.class);

    private final PersonService personService = new PersonService(personRepository, rabbitMqService);

    @Test
    void addPersonInDbTest() throws JsonProcessingException {
        personService.addPersonInDb(PersonMock.mock());
    }

    @Test
    void updatePersonInDbTest() throws JsonProcessingException {
        personService.updatePersonInDb(PersonMock.mock());
    }

    @Test
    void findAll() {
        personService.findAll(PageRequest.of(0, 10));
    }

    @Test
    void findById() {
        personService.findById(1L);
    }

    @Test
    void findByDocument() {
        personService.findByDocument(1L);
    }

    @Test
    void delete() {
        personService.delete(PersonMock.mock());
    }
}
