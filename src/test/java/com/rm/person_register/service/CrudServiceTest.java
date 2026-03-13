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
import org.mockito.ArgumentCaptor;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CrudServiceTest {
    private final PersonService personService = mock(PersonService.class);
    private final AddressService addressService = mock(AddressService.class);
    private final PersonMapper personMapper = mock(PersonMapper.class);
    private final AddressMapper addressMapper = mock(AddressMapper.class);
    private final PersonRepresentationMapper personRepresentationMapper = mock(PersonRepresentationMapper.class);
    private final Client client = mock(Client.class);

    private final CrudService crudService = new CrudService(personService, addressService, personMapper, addressMapper, personRepresentationMapper, client);

    @Test
    void getPersonByIdTest() {
        when(personService.findById(anyLong())).thenReturn(Optional.of(PersonMock.mock()));
        when(personRepresentationMapper.toRepresentation(any(), any())).thenReturn(PersonRepresentationMock.mock());

        crudService.getPersonById(1L);
    }

    @Test
    void getPersonByIdNullTest() {
        assertThrows(DataNotFoundException.class, () -> crudService.getPersonById(1L));
    }

    @Test
    void getPersonByDocumentTest() {
        when(personService.findByDocument(anyLong())).thenReturn(Optional.of(PersonMock.mock()));
        when(personRepresentationMapper.toRepresentation(any(), any())).thenReturn(PersonRepresentationMock.mock());

        crudService.getPersonByDocument(1L);
    }

    @Test
    void getPersonByDocumentNullTest() {
        assertThrows(DataNotFoundException.class, () -> crudService.getPersonByDocument(1L));
    }

    @Test
    void insertPersonTest() throws JsonProcessingException {
        when(personMapper.responseToDomain(any())).thenReturn(PersonMock.mock());
        when(addressMapper.responseToDomain(any())).thenReturn(AddressMock.mock());
        when(personRepresentationMapper.toRepresentation(any(), any())).thenReturn(PersonRepresentationMock.mock());

        crudService.insertPerson(RequestMock.mock());

        ArgumentCaptor<Person> captor = ArgumentCaptor.forClass(Person.class);
        verify(personService).addPersonInDb(captor.capture());

        Person value = captor.getValue();

        assertNull(value.getId());
    }

    @Test
    void insertPersonAlreadyExistsTest() {
        when(personMapper.responseToDomain(any())).thenReturn(PersonMock.mock());
        when(personService.findByDocument(any())).thenReturn(Optional.of(PersonMock.mock()));

        assertThrows(DataIntegrityViolationException.class, () -> crudService.insertPerson(RequestMock.mock()));
    }

    @Test
    void deletePersonTest() {
        when(personService.findByDocument(anyLong())).thenReturn(Optional.of(PersonMock.mock()));

        crudService.deletePerson(1L);

        ArgumentCaptor<Person> captor = ArgumentCaptor.forClass(Person.class);
        verify(personService).delete(captor.capture());

        Person value = captor.getValue();

        assertTrue(value.getId().equals(PersonMock.mock().getId()));
    }

    @Test
    void deletePersonNullTest() {
        assertThrows(DataNotFoundException.class, () -> crudService.deletePerson(1L));
    }

    @Test
    void putPersonTest() throws JsonProcessingException {
        when(personService.findByDocument(anyLong())).thenReturn(Optional.of(PersonMock.mock()));
        when(addressMapper.responseToDomain(any())).thenReturn(AddressMock.mock());

        crudService.putPerson(RequestMock.mock());

        ArgumentCaptor<Person> captor = ArgumentCaptor.forClass(Person.class);
        verify(personService).updatePersonInDb(captor.capture());

        Person value = captor.getValue();

        assertTrue(Objects.nonNull(value));
    }

    @Test
    void putPersonWithoutAddressInfoTest() throws JsonProcessingException {
        when(personService.findByDocument(anyLong())).thenReturn(Optional.of(PersonMock.mock()));

        crudService.putPerson(RequestMock.mockWithoutAddressInfo());

        ArgumentCaptor<Person> captor = ArgumentCaptor.forClass(Person.class);
        verify(personService).updatePersonInDb(captor.capture());

        Person value = captor.getValue();

        assertTrue(Objects.nonNull(value.getAddress()));
    }

    @Test
    void putPersonNotInDataBaseTest() {
        assertThrows(DataNotFoundException.class, () -> crudService.putPerson(RequestMock.mock()));
    }
}
