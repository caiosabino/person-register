package com.rm.person_register.mock;

import com.rm.person_register.data.dto.request.PersonDTO;

import java.time.LocalDate;

public class PersonDTOMock {
    public static PersonDTO mock() {
        return PersonDTO.builder()
                        .document(123L)
                        .name("teste")
                        .documentType("CPF")
                        .email("vendedor@teste.com")
                        .phone("11999998888")
                        .birthDate(LocalDate.now())
                        .build();
    }
}
