package com.rm.person_register.mock;

import com.rm.person_register.data.dto.request.Request;

public class RequestMock {
    public static Request mock() {
        return Request.builder()
                      .person(PersonDTOMock.mock())
                      .address(AddressDTOMock.mock())
                      .build();
    }

    public static Request mockWithoutAddressInfo() {
        return Request.builder()
                      .person(PersonDTOMock.mock())
                      .build();
    }
}
