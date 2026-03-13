package com.rm.person_register.mock;

import com.rm.person_register.data.dto.request.PersonDTO;

import java.util.Date;

public class PersonDTOMock {
    public static PersonDTO mock() {
        return PersonDTO.builder()
                        .document(123L)
                        .name("teste")
                        .documentType("CPF")
                        .email("vendedor@teste.com")
                        .phone("11999998888")
                        .tradeName("Loja Teste")
                        .stateRegistration("123456789")
                        .businessCategory("Eletronicos")
                        .birthDate(new Date())
                        .build();
    }
}
