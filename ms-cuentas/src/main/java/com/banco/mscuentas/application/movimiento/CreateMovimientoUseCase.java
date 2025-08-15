// CreateMovimientoUseCase.java
package com.banco.mscuentas.application.movimiento;

import com.banco.mscuentas.api.dto.*;
import com.banco.mscuentas.infrastructure.jpa.entity.MovimientoEntity;
import com.banco.mscuentas.infrastructure.jpa.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.UUID;

@Service
public class CreateMovimientoUseCase {
  private final CuentaRepository cuentaRepo;
  private final MovimientoRepository movRepo;

  public CreateMovimientoUseCase(CuentaRepository c, MovimientoRepository m){ this.cuentaRepo=c; this.movRepo=m; }

  @Transactional
  public MovimientoResponse handle(MovimientoCreateRequest req){
    var cuenta = cuentaRepo.findById(req.cuentaId()).orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada"));
    var last = movRepo.findTopByCuenta_CuentaIdOrderByFechaDescMovimientoIdDesc(req.cuentaId()).orElse(null);
    var currentSaldo = (last != null) ? last.getSaldo() : cuenta.getSaldoInicial();

    var tipo = req.tipoMovimiento().toUpperCase(Locale.ROOT);
    BigDecimal nuevoSaldo = switch (tipo) {
      case "DEPOSITO" -> currentSaldo.add(req.valor());
      case "RETIRO"   -> currentSaldo.subtract(req.valor());
      default -> throw new IllegalArgumentException("tipoMovimiento inválido (DEPOSITO|RETIRO)");
    };

    if (nuevoSaldo.compareTo(BigDecimal.ZERO) < 0)
      throw new IllegalArgumentException("Saldo insuficiente");

    var e = new MovimientoEntity();
    e.setCuenta(cuenta);
    e.setFecha(req.fecha());
    e.setTipoMovimiento(tipo);
    e.setValor(req.valor());
    e.setSaldo(nuevoSaldo);

    var saved = movRepo.save(e);
    return new MovimientoResponse(saved.getMovimientoId(), cuenta.getCuentaId(), saved.getFecha(), saved.getTipoMovimiento(), saved.getValor(), saved.getSaldo());
  }
}
