package com.rm.person_register.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rm.person_register.data.dto.request.Request;
import com.rm.person_register.data.dto.response.PersonRepresentation;
import com.rm.person_register.service.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/person")
public class CrudController {

    private final CrudService crudService;

    @GetMapping
    public Page<PersonRepresentation> getAllPerson(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return crudService.getAllPerson(pageable);
    }

    @GetMapping("/id/{id}")
    public PersonRepresentation getPersonById(@PathVariable(value = "id") Long id) {
        return crudService.getPersonById(id);
    }

    @GetMapping("/document/{document}")
    public PersonRepresentation getPersonByDocument(@PathVariable(value = "document") Long document) {
        return crudService.getPersonByDocument(document);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void insertPerson(@RequestBody Request request) throws JsonProcessingException {
        crudService.insertPerson(request);
    }

    @DeleteMapping("/document/{document}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePerson(@PathVariable(value = "document") Long document) {
        crudService.deletePerson(document);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void putPerson(@RequestBody Request request) throws JsonProcessingException {
        crudService.putPerson(request);
    }
}