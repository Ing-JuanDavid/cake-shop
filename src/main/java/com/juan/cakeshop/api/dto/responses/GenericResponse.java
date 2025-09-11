package com.juan.cakeshop.api.dto.responses;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class GenericResponse<T> {
    private String message;
    private T data;
    private String error;
}
