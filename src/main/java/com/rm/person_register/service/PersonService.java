package com.rm.person_register.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
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
    @Value("${kafka.topics.person-register}")
    private String topicPersonRegisterName;

    private final PersonRepository personRepository;
    private final KafkaService kafkaService;

    protected void addPersonInDb(Person person) throws JsonProcessingException {
        person.setCreatedAt(new Date());
        personRepository.save(person);

        kafkaService.sendToTopic(topicPersonRegisterName, person);
    }

    protected void updatePersonInDb(Person person) throws JsonProcessingException {
        person.setUpdatedAt(new Date());
        personRepository.save(person);

        kafkaService.sendToTopic(topicPersonRegisterName, person);
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

    protected void delete(Person person){
        personRepository.delete(person);
    }
}
