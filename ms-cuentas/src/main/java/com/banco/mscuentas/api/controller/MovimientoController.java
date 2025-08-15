package com.banco.mscuentas.api.controller;

import com.banco.mscuentas.api.dto.*;
import com.banco.mscuentas.application.movimiento.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {
  private final CreateMovimientoUseCase createUC;
  private final ListMovimientosUseCase listUC;
  private final UpdateMovimientoUseCase updateUC;

  public MovimientoController(CreateMovimientoUseCase c, ListMovimientosUseCase l, UpdateMovimientoUseCase u){
    this.createUC=c; this.listUC=l; this.updateUC=u;
  }

  @PostMapping
  public MovimientoResponse create(@Valid @RequestBody MovimientoCreateRequest req){
    return createUC.handle(req);
  }

  @GetMapping
  public Page<MovimientoResponse> list(@RequestParam UUID cuentaId, Pageable pageable){
    return listUC.byCuenta(cuentaId, pageable);
  }

  @PutMapping("/{movimientoId}")
  public MovimientoResponse update(@PathVariable UUID movimientoId, @Valid @RequestBody MovimientoUpdateRequest req){
    return updateUC.handle(movimientoId, req);
  }
}
