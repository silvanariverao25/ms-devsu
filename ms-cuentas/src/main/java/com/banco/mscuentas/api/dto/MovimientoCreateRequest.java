// Movimiento DTOs
package com.banco.mscuentas.api.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MovimientoCreateRequest(
		  @NotNull UUID cuentaId,
		  @NotNull OffsetDateTime fecha,
		  @NotBlank String tipoMovimiento,           // DEPOSITO | RETIRO
		  @NotNull @Digits(integer=16, fraction=2) BigDecimal valor
		) {}