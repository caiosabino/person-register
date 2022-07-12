package com.rm.person_register.mock;

import com.rm.person_register.data.dto.response.PersonRepresentation;

public class PersonRepresentationMock {
    public static PersonRepresentation mock() {
        return PersonRepresentation.builder()
                                   .person(PersonDTOMock.mock())
                                   .address(AddressDTOMock.mock())
                                   .build();
    }
}
