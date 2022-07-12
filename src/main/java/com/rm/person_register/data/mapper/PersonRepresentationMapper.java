package com.rm.person_register.data.mapper;

import com.rm.person_register.data.dto.request.AddressDTO;
import com.rm.person_register.data.dto.request.PersonDTO;
import com.rm.person_register.data.dto.response.PersonRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDateTime;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", imports = LocalDateTime.class)
public interface PersonRepresentationMapper {

    @Mapping(expression = "java(LocalDateTime.now())", target = "timestampGet")
    PersonRepresentation toRepresentation(PersonDTO person, AddressDTO address);
}
