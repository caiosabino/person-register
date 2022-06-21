package com.rm.person_register.data.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;

@Setter
@ToString
@EqualsAndHashCode
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderClassName = "ReturnBuilder", toBuilder = true)
@JsonDeserialize(builder = Return.ReturnBuilder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Return {
    public String status;

    public String message;

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ReturnBuilder {

    }
}
