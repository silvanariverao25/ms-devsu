package com.banco.mscuentas.api.controller;

import com.banco.mscuentas.api.dto.*;
import com.banco.mscuentas.application.movimiento.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@RestController
@RequestMapping("/reportes")
public class ReporteController {
	private final ListMovimientosUseCase listUC;

	public ReporteController(ListMovimientosUseCase l) {
		this.listUC = l;
	}

	@GetMapping
	public Page<MovimientoResponse> list(@RequestParam UUID cuentaId, @RequestParam LocalDate fechadesde,
			@RequestParam LocalDate fechahasta, Pageable pageable) {
		ZoneOffset offset = ZoneOffset.of("-05:00"); //Ecuador
		OffsetDateTime desde = fechadesde.atStartOfDay().atOffset(offset);
		OffsetDateTime hasta = fechahasta.atTime(LocalTime.MAX).atOffset(offset);

		return listUC.byCuentaYFechas(cuentaId, desde, hasta, pageable);
	}
}
