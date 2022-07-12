package com.rm.person_register.data.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDTO {
    private Long id;

    @NonNull
    private String street;

    @NonNull
    private String neighborhood;

    @NonNull
    private String city;

    @NonNull
    private String number;

    private String complement;
}
