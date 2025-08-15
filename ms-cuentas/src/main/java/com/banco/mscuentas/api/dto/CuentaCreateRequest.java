// Cuenta DTOs
package com.banco.mscuentas.api.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.UUID;

public record CuentaCreateRequest(
  @NotNull UUID clienteId,
  @NotBlank String numeroCuenta,
  @NotBlank String tipoCuenta,
  @NotNull @Digits(integer=16, fraction=2) BigDecimal saldoInicial,
  @Min(0) @Max(1) short estado
) {}