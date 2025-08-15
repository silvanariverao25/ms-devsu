// Movimiento DTOs
package com.banco.mscuentas.api.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record MovimientoResponse(
		  UUID movimientoId,
		  UUID cuentaId,
		  OffsetDateTime fecha,
		  String tipoMovimiento,
		  BigDecimal valor,
		  BigDecimal saldo
		) {}