package com.rm.person_register.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rm.person_register.data.entity.Person;
import com.rm.person_register.data.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonService {
    @Value("${rabbitmq.queues.person-register}")
    private String personRegisterQueueName;

    private final PersonRepository personRepository;
    private final RabbitMqService rabbitMqService;

    protected void addPersonInDb(Person person) throws JsonProcessingException {
        person.setCreatedAt(new Date());
        personRepository.save(person);

        rabbitMqService.sendToQueue(personRegisterQueueName, person);
    }

    protected void updatePersonInDb(Person person) throws JsonProcessingException {
        person.setUpdatedAt(new Date());
        personRepository.save(person);

        rabbitMqService.sendToQueue(personRegisterQueueName, person);
    }

    protected Page<Person> findAll(Pageable pageable) {
        return personRepository.findAll(pageable);
    }

    protected Optional<Person> findById(Long id) {
        return personRepository.findById(id);
    }

    protected Optional<Person> findByDocument(Long document) {
        return personRepository.findByDocument(document);
    }

    protected void delete(Person person) {
        personRepository.delete(person);
    }
}
