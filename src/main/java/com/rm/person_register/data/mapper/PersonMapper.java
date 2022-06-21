package com.rm.person_register.data.mapper;

import com.rm.person_register.data.dto.PersonDTO;
import com.rm.person_register.data.entity.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDateTime;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", imports = LocalDateTime.class)
public interface PersonMapper {

    @Mapping(expression = "java(LocalDateTime.now())", target = "timestampGet")
    PersonDTO domainToResponse(Person person);
}
