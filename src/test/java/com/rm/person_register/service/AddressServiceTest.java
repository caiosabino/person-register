package com.rm.person_register.service;

import com.rm.person_register.data.mapper.AddressMapper;
import com.rm.person_register.data.repository.AddressRepository;
import com.rm.person_register.data.repository.PersonRepository;
import com.rm.person_register.exception.DataNotFoundException;
import com.rm.person_register.mock.AddressDTOMock;
import com.rm.person_register.mock.AddressMock;
import com.rm.person_register.mock.PersonDTOMock;
import com.rm.person_register.mock.PersonMock;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
class AddressServiceTest {
    private final PersonRepository personRepository = mock(PersonRepository.class);
    private final AddressRepository addressRepository = mock(AddressRepository.class);
    private final AddressMapper addressMapper = mock(AddressMapper.class);

    private AddressService addressService = new AddressService(personRepository, addressRepository, addressMapper);


    @Test
    public void addAddressInDbTest() {
        when(addressMapper.responseToDomain(any())).thenReturn(AddressMock.mock());

        addressService.addAddressInDb(AddressDTOMock.mock());
    }

    @Test
    public void updateAddressInDbTest() {
        when(addressMapper.responseToDomain(any())).thenReturn(AddressMock.mock());
        when(personRepository.findByDocument(any())).thenReturn(Optional.of(PersonMock.mock()));

        addressService.addAddressInDb(AddressDTOMock.mock());
    }

    @Test
    public void updateAddressWhenPersonIsNotInDbTest() {
        when(addressMapper.responseToDomain(any())).thenReturn(AddressMock.mock());

        assertThrows(DataNotFoundException.class, () -> addressService.updateAddressInDb(PersonDTOMock.mock(), AddressDTOMock.mock()));
    }
}
