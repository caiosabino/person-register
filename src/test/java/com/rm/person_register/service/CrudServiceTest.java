package com.rm.person_register.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rm.person_register.client.Client;
import com.rm.person_register.data.entity.Person;
import com.rm.person_register.data.mapper.AddressMapper;
import com.rm.person_register.data.mapper.PersonMapper;
import com.rm.person_register.data.mapper.PersonRepresentationMapper;
import com.rm.person_register.exception.DataNotFoundException;
import com.rm.person_register.mock.AddressMock;
import com.rm.person_register.mock.PersonMock;
import com.rm.person_register.mock.PersonRepresentationMock;
import com.rm.person_register.mock.RequestMock;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Objects;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
class CrudServiceTest {
    private PersonService personService = mock(PersonService.class);
    private AddressService addressService = mock(AddressService.class);
    private PersonMapper personMapper = mock(PersonMapper.class);
    private AddressMapper addressMapper = mock(AddressMapper.class);
    private PersonRepresentationMapper personRepresentationMapper = mock(PersonRepresentationMapper.class);
    private Client client = mock(Client.class);

    private CrudService crudService = new CrudService(personService, addressService, personMapper, addressMapper, personRepresentationMapper, client);

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
        when(personService.findById(anyLong())).thenReturn(Optional.of(PersonMock.mock()));
        when(personRepresentationMapper.toRepresentation(any(), any())).thenReturn(PersonRepresentationMock.mock());

        crudService.getPersonById(1L);
    }

    @Test
    public void getPersonByIdNullTest() {
        assertThrows(DataNotFoundException.class, () -> crudService.getPersonById(1L));
    }

    @Test
    public void getPersonByDocumentTest() {
        when(personService.findByDocument(anyLong())).thenReturn(Optional.of(PersonMock.mock()));
        when(personRepresentationMapper.toRepresentation(any(), any())).thenReturn(PersonRepresentationMock.mock());

        crudService.getPersonByDocument(1L);
    }

    @Test
    public void getPersonByDocumentNullTest() {
        assertThrows(DataNotFoundException.class, () -> crudService.getPersonByDocument(1L));
    }

    @Test
    public void insertPersonTest() throws JsonProcessingException {
        when(personMapper.responseToDomain(any())).thenReturn(PersonMock.mock());
        when(addressMapper.responseToDomain(any())).thenReturn(AddressMock.mock());
        when(personRepresentationMapper.toRepresentation(any(), any())).thenReturn(PersonRepresentationMock.mock());

        crudService.insertPerson(RequestMock.mock());

        ArgumentCaptor<Person> captor = ArgumentCaptor.forClass(Person.class);
        verify(personService).addPersonInDb(captor.capture());

        Person value = captor.getValue();

        assertTrue(value.getId().equals(PersonMock.mock().getId()));
    }

    @Test
    public void insertPersonAlreadyExistsTest() {
        when(personMapper.responseToDomain(any())).thenReturn(PersonMock.mock());
        when(personService.findByDocument(any())).thenReturn(Optional.of(PersonMock.mock()));

        assertThrows(DataIntegrityViolationException.class, () -> crudService.insertPerson(RequestMock.mock()));
    }

    @Test
    public void deletePersonTest() {
        when(personService.findByDocument(anyLong())).thenReturn(Optional.of(PersonMock.mock()));

        crudService.deletePerson(1l);

        ArgumentCaptor<Person> captor = ArgumentCaptor.forClass(Person.class);
        verify(personService).delete(captor.capture());

        Person value = captor.getValue();

        assertTrue(value.getId().equals(PersonMock.mock().getId()));
    }

    @Test
    public void deletePersonNullTest() {
        assertThrows(DataNotFoundException.class, () -> crudService.deletePerson(1l));
    }

    @Test
    public void putPersonTest() throws JsonProcessingException {
        when(personService.findByDocument(anyLong())).thenReturn(Optional.of(PersonMock.mock()));
        when(addressMapper.responseToDomain(any())).thenReturn(AddressMock.mock());

        crudService.putPerson(RequestMock.mock());

        ArgumentCaptor<Person> captor = ArgumentCaptor.forClass(Person.class);
        verify(personService).updatePersonInDb(captor.capture());

        Person value = captor.getValue();

        assertTrue(Objects.nonNull(value));
    }

    @Test
    public void putPersonWithoutAddressInfoTest() throws JsonProcessingException {
        when(personService.findByDocument(anyLong())).thenReturn(Optional.of(PersonMock.mock()));

        crudService.putPerson(RequestMock.mockWithoutAddressInfo());

        ArgumentCaptor<Person> captor = ArgumentCaptor.forClass(Person.class);
        verify(personService).updatePersonInDb(captor.capture());

        Person value = captor.getValue();

        assertTrue(Objects.nonNull(value.getAddress()));
    }

    @Test
    public void putPersonNotInDataBaseTest() {
        assertThrows(DataNotFoundException.class, () -> crudService.putPerson(RequestMock.mock()));
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
