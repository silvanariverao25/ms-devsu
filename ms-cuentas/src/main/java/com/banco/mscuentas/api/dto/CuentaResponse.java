// Cuenta DTOs
package com.banco.mscuentas.api.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.UUID;

public record CuentaResponse(
		  UUID cuentaId,
		  UUID clienteId,
		  String numeroCuenta,
		  String tipoCuenta,
		  java.math.BigDecimal saldoInicial,
		  short estado
		) {}