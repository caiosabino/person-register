package com.rm.person_register.mock;

import com.rm.person_register.data.dto.request.PersonDTO;

public class PersonDTOMock {
    public static PersonDTO mock() {
        return PersonDTO.builder()
                        .document(123L)
                        .name("teste")
                        .build();
    }
}
