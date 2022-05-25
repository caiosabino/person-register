package com.rm.personregister;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rm.personregister.data.dto.PersonDTO;
import com.rm.personregister.data.entity.Person;
import com.rm.personregister.data.mapper.PersonMapper;
import com.rm.personregister.data.repository.PersonRepository;
import com.rm.personregister.service.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
//import org.springframework.kafka.core.KafkaTemplate;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = PersonService.class)
public class PersonServiceTest {
    @InjectMocks
    private PersonService personService;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private PersonMapper personMapper;

    @Mock
    private Pageable pageableMock;

//    @Mock
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
        when(personMapper.domainToResponse((Person) any())).thenReturn(personDTOMock());

        personService.getPersonById(1L);
    }

    @Test
    public void getPersonByIdNullTest() {
        personService.getPersonById(1L);
    }

    @Test
    public void getPersonByDocumentTest() {
        when(personRepository.findByDocument(anyLong())).thenReturn(Optional.of(personMock()));
        when(personMapper.domainToResponse((Person) any())).thenReturn(personDTOMock());

        personService.getPersonByDocument(1L);
    }

    @Test
    public void getPersonByDocumentNullTest() {
        personService.getPersonByDocument(1L);
    }

    @Test
    public void insertPersonTest() {
//        when(template.send(anyString(), anyString())).thenAnswer(new Answer<Object>() {
//            @Override
//            public Object answer(InvocationOnMock invocation) throws Throwable {
//                return null;
//            }
//        });
        when(personMapper.domainToResponse((Person) any())).thenReturn(personDTOMock());

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
