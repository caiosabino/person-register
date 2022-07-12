package com.rm.person_register.mock;

import com.rm.person_register.data.dto.request.AddressDTO;

public class AddressDTOMock {
    public static AddressDTO mock() {
        return AddressDTO.builder()
                         .street("Rua Teste")
                         .number("1")
                         .city("Cidade Teste")
                         .neighborhood("Bairro Teste")
                         .complement("")
                         .build();
    }
}
