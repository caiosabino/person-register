package com.rm.person_register.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rm.person_register.client.Client;
import com.rm.person_register.data.dto.request.Request;
import com.rm.person_register.data.dto.response.PersonRepresentation;
import com.rm.person_register.data.entity.Person;
import com.rm.person_register.data.mapper.AddressMapper;
import com.rm.person_register.data.mapper.PersonMapper;
import com.rm.person_register.data.mapper.PersonRepresentationMapper;
import com.rm.person_register.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class CrudService {
    private static final String DATA_NOT_FOUND_MESSAGE = "Register not found in database";
    private static final String DATA_INTEGRITY_VIOLATION_MESSAGE = "Register already in database";

    private final PersonService personService;
    private final AddressService addressService;
    private final PersonMapper personMapper;
    private final AddressMapper addressMapper;
    private final PersonRepresentationMapper personRepresentationMapper;
    private final Client client;

    public Page<PersonRepresentation> getAllPerson(Pageable pageable) {
        ObjectNode poke = client.pokeIntegration();
        return personService.findAll(pageable)
                            .map(person -> personRepresentationMapper.toRepresentation(personMapper.domainToResponse(person), addressMapper.domainToResponse(person.getAddress())));
    }

    public PersonRepresentation getPersonById(Long id) {
        Person person = personService.findById(id)
                                     .orElseThrow(() -> new DataNotFoundException(DATA_NOT_FOUND_MESSAGE));

        return personRepresentationMapper.toRepresentation(personMapper.domainToResponse(person), addressMapper.domainToResponse(person.getAddress()));
    }

    public PersonRepresentation getPersonByDocument(Long document) {
        Person person = personService.findByDocument(document)
                                     .orElseThrow(() -> new DataNotFoundException(DATA_NOT_FOUND_MESSAGE));

        PersonRepresentation personRepresentation = personRepresentationMapper.toRepresentation(personMapper.domainToResponse(person), addressMapper.domainToResponse(person.getAddress()));

        return personRepresentation;
    }

    public void insertPerson(Request request) throws JsonProcessingException {
        Person person = personMapper.responseToDomain(request.getPerson());

        if (personService.findByDocument(request.getPerson()
                                                .getDocument())
                         .isPresent()) {
            throw new DataIntegrityViolationException(DATA_INTEGRITY_VIOLATION_MESSAGE);
        }

        person.setAddress(addressService.addAddressInDb(request.getAddress()));

        personService.addPersonInDb(person);
    }

    public void deletePerson(Long document) {
        Person person = personService.findByDocument(document)
                                     .orElseThrow(() -> new DataNotFoundException(DATA_NOT_FOUND_MESSAGE));

        personService.delete(person);
    }

    public void putPerson(Request request) throws JsonProcessingException {
        Person person = personService.findByDocument(request.getPerson()
                                                            .getDocument())
                                     .orElseThrow(() -> new DataNotFoundException(DATA_NOT_FOUND_MESSAGE));

        person.setName(request.getPerson()
                              .getName());

        if (Objects.nonNull(request.getAddress())) {
            person.setAddress(addressService.updateAddressInDb(request.getPerson(), request.getAddress()));
        }

        personService.updatePersonInDb(person);
    }
}