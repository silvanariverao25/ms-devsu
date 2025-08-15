package com.banco.msclientes.api.dto;

import jakarta.validation.constraints.*;

public record ClienteUpdateRequest(
        @NotBlank @Size(max = 120) String nombre,
        @NotBlank @Size(min = 1, max = 20) String genero,
        @Min(0) @Max(150) short edad,
        @NotBlank @Size(max = 200) String direccion,
        @NotBlank @Size(max = 30) String telefono,
        @Min(0) @Max(1) short estado
) {}
