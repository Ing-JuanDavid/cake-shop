package com.juan.cakeshop.api.dto.requests;

import com.juan.cakeshop.api.model.User;
import jakarta.annotation.Nullable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

@Data
@Builder
public class UserAddressDto {
    @NotBlank(message = "La ciudad es obligatoria")
    private String city;
    @NotBlank(message = "El departamento es obligatorio")
    private String department;
    @NotBlank(message = "La direccion es obligatoria")
    private String addressLine;
    private String description;
    private boolean isDefault;
}
