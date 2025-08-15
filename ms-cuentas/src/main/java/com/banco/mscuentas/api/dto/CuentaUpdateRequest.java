// Cuenta DTOs
package com.banco.mscuentas.api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CuentaUpdateRequest(
  @NotBlank String tipoCuenta,
  @Min(0) @Max(1) short estado
) {}