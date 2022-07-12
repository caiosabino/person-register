package com.rm.person_register.service;

import com.rm.person_register.data.dto.request.AddressDTO;
import com.rm.person_register.data.dto.request.PersonDTO;
import com.rm.person_register.data.entity.Address;
import com.rm.person_register.data.entity.Person;
import com.rm.person_register.data.mapper.AddressMapper;
import com.rm.person_register.data.repository.AddressRepository;
import com.rm.person_register.data.repository.PersonRepository;
import com.rm.person_register.exception.DataNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

@Service
@AllArgsConstructor
public class AddressService {
    private static final String DATA_NOT_FOUND_MESSAGE = "Register not found in database";

    private final PersonRepository personRepository;
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    protected Address addAddressInDb(AddressDTO addressDto) {
        Address address = addressMapper.responseToDomain(addressDto);
        address.setCreatedAt(new Date());
        return addressRepository.save(address);
    }

    protected Address updateAddressInDb(PersonDTO personDto, AddressDTO addressDto) {
        Address address = addressMapper.responseToDomain(addressDto);
        Person person = personRepository.findByDocument(personDto.getDocument())
                                        .orElseThrow(() -> new DataNotFoundException(DATA_NOT_FOUND_MESSAGE));

        address.setId(person.getAddress().getId());
        address.setCreatedAt(Objects.isNull(person.getCreatedAt()) ? new Date() : person.getAddress().getCreatedAt());
        address.setUpdatedAt(new Date());

        return addressRepository.save(address);
    }
}
