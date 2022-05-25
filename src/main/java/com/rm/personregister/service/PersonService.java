package com.rm.personregister.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rm.personregister.client.TestClient;
import com.rm.personregister.data.dto.PersonDTO;
import com.rm.personregister.data.dto.Return;
import com.rm.personregister.data.entity.Person;
import com.rm.personregister.data.mapper.PersonMapper;
import com.rm.personregister.data.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PersonService {
    private static final String NOT_FOUND_MESSAGE = "Not found";
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;
    private final TestClient client;
//    private final KafkaTemplate template;

    private Return ret;

    public ResponseEntity<Object> getAllPerson(Pageable pageable) {
        ObjectNode poke = client.pokeIntegration();
        Page<PersonDTO> people = personRepository.findAll(pageable).map(personMapper::domainToResponse);

        return ResponseEntity.status(HttpStatus.OK)
                             .body(people);
    }

    public ResponseEntity<Object> getPersonById(Long id) {
        Person person = personRepository.findById(id)
                                        .orElse(null);
        if (Optional.ofNullable(person).isPresent()) {
            return ResponseEntity.status(HttpStatus.OK)
                                 .body(personMapper.domainToResponse(person));
        } else {
            ret = buildReturn(NOT_FOUND_MESSAGE, HttpStatus.NOT_FOUND.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(ret);
        }
    }

    public ResponseEntity<Object> getPersonByDocument(Long document) {
        Person person = personRepository.findByDocument(document)
                                        .orElse(null);
        if (Optional.ofNullable(person).isPresent()) {
            return ResponseEntity.status(HttpStatus.OK)
                                 .body(personMapper.domainToResponse(person));
        } else {
            ret = buildReturn(NOT_FOUND_MESSAGE, HttpStatus.NOT_FOUND.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(ret);
        }
    }

    public ResponseEntity<Object> insertPerson(Person person) {
        try {
            personRepository.save(person);

//            template.send("api-spring-topic", personMapper.domainToResponse(person)
//                                                          .toString());
        } catch (DataIntegrityViolationException ex) {
            ret = buildReturn("Request can't be saved in database", HttpStatus.BAD_REQUEST.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(ret);
        }
        ret = buildReturn("Created", HttpStatus.CREATED.toString());
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(ret);
    }

    public ResponseEntity<Object> deletePerson(Long document) {
        Person person = personRepository.findByDocument(document)
                                        .orElse(null);

        if (Optional.ofNullable(person).isPresent()) {
            personRepository.delete(person);
            ret = buildReturn("Deleted", HttpStatus.OK.toString());
        } else {
            ret = buildReturn("Document Not in Database", HttpStatus.NOT_FOUND.toString());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(ret);
    }

    public ResponseEntity<Object> putPerson(Person person) {
        Person personInDb = personRepository.findByDocument(person.getDocument())
                                            .orElse(null);

        if (Optional.ofNullable(personInDb).isPresent()) {
            if (person.getId()
                      .equals(personInDb.getId())) {
                personRepository.save(person);
                ret = buildReturn("Change With Success", HttpStatus.OK.toString());
            } else {
                ret = buildReturn("The id is not the same of this document", HttpStatus.NOT_FOUND.toString());
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body(ret);
            }
            return ResponseEntity.status(HttpStatus.OK)
                                 .body(ret);
        } else {
            ret = buildReturn("Document Not in Database", HttpStatus.NOT_FOUND.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(ret);
        }
    }

    public Return buildReturn(String message, String status) {
        return Return.builder()
                     .message(message)
                     .status(status)
                     .build();
    }
}