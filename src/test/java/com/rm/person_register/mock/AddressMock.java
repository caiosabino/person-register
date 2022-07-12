package com.rm.person_register.mock;

import com.rm.person_register.data.entity.Address;

import java.util.Date;

public class AddressMock {
    public static Address mock() {
        Address address = new Address();

        address.setId(1L);
        address.setStreet("Rua Teste");
        address.setNumber("1");
        address.setCity("Cidade Teste");
        address.setNeighborhood("Bairro Teste");
        address.setCreatedAt(new Date());

        return address;
    }
}
