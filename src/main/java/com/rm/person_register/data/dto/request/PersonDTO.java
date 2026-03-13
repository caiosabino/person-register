package com.rm.person_register.data.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonDTO {
    private Long id;

    @NonNull
    private Long document;

    @NonNull
    private String name;

    private String documentType;

    private String email;

    private String phone;

    private LocalDate birthDate;
}
