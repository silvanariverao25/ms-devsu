// Movimiento DTOs
package com.banco.mscuentas.api.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MovimientoUpdateRequest(
		  @NotNull OffsetDateTime fecha,
		  @NotBlank String tipoMovimiento,
		  @NotNull @Digits(integer=16, fraction=2) BigDecimal valor
		) {}
