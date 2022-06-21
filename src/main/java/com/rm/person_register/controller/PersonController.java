package com.rm.person_register.controller;

import com.rm.person_register.data.entity.Person;
import com.rm.person_register.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/api/person")
public class PersonController {

	private final PersonService personService;

	@GetMapping
	public ResponseEntity<Object> getAllPerson(@PageableDefault(sort="id", direction= Sort.Direction.DESC) Pageable pageable){
		return personService.getAllPerson(pageable);
	}

	@GetMapping("/id/{id}")
	public ResponseEntity<Object> getPersonById(@PathVariable(value="id") Long id){
		return personService.getPersonById(id);
	}
	
	@GetMapping("/document/{document}")
	public ResponseEntity<Object> getPersonByDocument(@PathVariable(value="document") Long document){
		return personService.getPersonByDocument(document);
	}

	@PostMapping
	public ResponseEntity<Object> insertPerson(@RequestBody Person person){
		return personService.insertPerson(person);
	}

	@DeleteMapping("/document/{document}")
	public ResponseEntity<Object> deletePerson(@PathVariable(value="document") Long document){
		return personService.deletePerson(document);
	}

	@PutMapping
	public ResponseEntity<Object> putPerson(@RequestBody Person person){
		return personService.putPerson(person);
	}
}