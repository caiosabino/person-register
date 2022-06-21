package com.rm.person_register;

import com.rm.person_register.data.dto.PersonDTO;
import com.rm.person_register.data.entity.Person;
import com.rm.person_register.data.mapper.PersonMapper;
import com.rm.person_register.data.repository.PersonRepository;
import com.rm.person_register.service.PersonService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PersonService.class)
class PersonServiceTest {
    @Autowired
    private PersonService personService;

    @MockBean
    private PersonRepository personRepository;

    @MockBean
    private PersonMapper personMapper;

    @MockBean
    private Pageable pageableMock;

//    @MockBean
//    private KafkaTemplate template;

//    @Test
//    public void getAllPersonTest() {
//        Pageable pageable = PageRequest.of(0, 1);
//        when(personRepository.findAll((Pageable) any())).thenReturn((Page<Person>) pageableMock);
//        when(personMapper.domainToResponse(any())).thenReturn(personDTOMock());
//
//        personService.getAllPerson(pageable);
//    }

    @Test
    public void getPersonByIdTest() {
        when(personRepository.findById(anyLong())).thenReturn(Optional.of(personMock()));
        when(personMapper.domainToResponse(any())).thenReturn(personDTOMock());

        personService.getPersonById(1L);
    }

    @Test
    public void getPersonByIdNullTest() {
        personService.getPersonById(1L);
    }

    @Test
    public void getPersonByDocumentTest() {
        when(personRepository.findByDocument(anyLong())).thenReturn(Optional.of(personMock()));
        when(personMapper.domainToResponse(any())).thenReturn(personDTOMock());

        personService.getPersonByDocument(1L);
    }

    @Test
    public void getPersonByDocumentNullTest() {
        personService.getPersonByDocument(1L);
    }

    @Test
    public void insertPersonTest() {
        when(personMapper.domainToResponse(any())).thenReturn(personDTOMock());

        personService.insertPerson(personMock());
    }

    @Test
    public void insetPersonNullTest() {
        when(personRepository.save(any())).thenThrow(DataIntegrityViolationException.class);

        personService.insertPerson(personMock());
    }

    @Test
    public void deletePersonTest() {
        when(personRepository.findByDocument(anyLong())).thenReturn(Optional.of(personMock()));

        personService.deletePerson(1l);
    }

    @Test
    public void deletePersonNullTest() {
        personService.deletePerson(1l);
    }

    @Test
    public void putPersonTest() {
        when(personRepository.findByDocument(anyLong())).thenReturn(Optional.of(personMock()));

        personService.putPerson(personMock());
    }

    @Test
    public void putPersonDifferentIdTest() {
        when(personRepository.findByDocument(anyLong())).thenReturn(Optional.of(personMock()));

        Person person = personMock();
        person.setId(2L);

        personService.putPerson(person);
    }

    @Test
    public void putPersonNullTest() {
        personService.putPerson(personMock());
    }

    private Person personMock() {
        Person person = new Person();
        person.setId(1L);
        person.setDocument(123L);
        person.setName("teste");

        return person;
    }

    private PersonDTO personDTOMock() {
        return PersonDTO.builder()
                        .id(1L)
                        .document(123L)
                        .name("teste")
                        .build();
    }

    //    	private List<Person> buildPersonResponseList(String responseFilePath) throws IOException {
    //		Resource personResponse = resourceLoader.getResource("classpath:" + responseFilePath);
    //
    //		return objectMapper.readValue(personResponse.getInputStream(), List<Person>.class);
    //	}

    //	private Person buildPersonResponse(String responseFilePath) throws IOException {
    //		Resource personResponse = resourceLoader.getResource("classpath:" + responseFilePath);
    //
    //		return objectMapper.readValue(personResponse.getInputStream(), Person.class);
    //	}
}
