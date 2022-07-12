package com.rm.person_register.data.mapper;

import com.rm.person_register.data.dto.request.AddressDTO;
import com.rm.person_register.data.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDateTime;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", imports = LocalDateTime.class)
public interface AddressMapper {
    Address responseToDomain(AddressDTO addressDTO);

    AddressDTO domainToResponse(Address address);
}
