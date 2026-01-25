package com.juan.cakeshop.api.dto.responses;

import lombok.Builder;
import lombok.Data;
import java.util.Map;

@Data
@Builder
public class FieldErrorsResponse {
    private boolean ok;
    private String error;
    private Map<String, String> fieldErrors;
}
