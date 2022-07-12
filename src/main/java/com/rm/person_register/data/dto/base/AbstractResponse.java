package com.rm.person_register.data.dto.base;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public abstract class AbstractResponse<T> {

    protected T data;

    protected List<String> message;

    public void addMessage(String strMessage){
        if (message == null) {
            message = new ArrayList<>();
        }

        message.add(strMessage);
    }
}
