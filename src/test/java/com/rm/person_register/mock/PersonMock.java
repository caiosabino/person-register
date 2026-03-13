package com.rm.person_register.mock;

import com.rm.person_register.data.entity.Person;

import java.util.Date;

public class PersonMock {
    public static Person mock() {
        Person person = new Person();

        person.setId(1L);
        person.setDocument(123L);
        person.setName("teste");
        person.setDocumentType("CPF");
        person.setEmail("vendedor@teste.com");
        person.setPhone("11999998888");
        person.setTradeName("Loja Teste");
        person.setStateRegistration("123456789");
        person.setBusinessCategory("Eletronicos");
        person.setBirthDate(new Date());
        person.setCreatedAt(new Date());
        person.setAddress(AddressMock.mock());

        return person;
    }
}
