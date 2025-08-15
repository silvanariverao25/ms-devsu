// UpdateMovimientoUseCase.java
package com.banco.mscuentas.application.movimiento;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Locale;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banco.mscuentas.api.dto.MovimientoResponse;
import com.banco.mscuentas.api.dto.MovimientoUpdateRequest;
import com.banco.mscuentas.infrastructure.jpa.entity.MovimientoEntity;
import com.banco.mscuentas.infrastructure.jpa.repository.CuentaRepository;
import com.banco.mscuentas.infrastructure.jpa.repository.MovimientoRepository;

@Service
public class UpdateMovimientoUseCase {
  private final MovimientoRepository movRepo;
  private final CuentaRepository cuentaRepo;

  public UpdateMovimientoUseCase(MovimientoRepository m, CuentaRepository c){ this.movRepo=m; this.cuentaRepo=c; }

  @Transactional
  public MovimientoResponse handle(UUID movimientoId, MovimientoUpdateRequest req){
    var mov = movRepo.findById(movimientoId).orElseThrow(() -> new IllegalArgumentException("Movimiento no encontrado"));
    var cuenta = mov.getCuenta();

    // Actualiza campos
    mov.setFecha(req.fecha());
    mov.setTipoMovimiento(req.tipoMovimiento().toUpperCase(Locale.ROOT));
    mov.setValor(req.valor());
    movRepo.save(mov);

    // Recalcula TODOS los saldos de la cuenta por orden de fecha (simple y seguro)
    var movimientos = movRepo.findByCuenta_CuentaId(cuenta.getCuentaId(), Pageable.unpaged()).getContent();
    movimientos.sort(Comparator.comparing(MovimientoEntity::getFecha).thenComparing(MovimientoEntity::getMovimientoId));
    var saldo = cuenta.getSaldoInicial();
    for (var m : movimientos){
      saldo = switch (m.getTipoMovimiento()){
        case "DEPOSITO" -> saldo.add(m.getValor());
        case "RETIRO"   -> saldo.subtract(m.getValor());
        default -> throw new IllegalArgumentException("tipoMovimiento inválido");
      };
      if (saldo.compareTo(BigDecimal.ZERO) < 0)
        throw new IllegalArgumentException("Saldo insuficiente al recalcular");
      m.setSaldo(saldo);
    }
    movRepo.saveAll(movimientos);

    var updated = movRepo.findById(movimientoId).orElseThrow();
    return new MovimientoResponse(updated.getMovimientoId(), cuenta.getCuentaId(), updated.getFecha(), updated.getTipoMovimiento(), updated.getValor(), updated.getSaldo());
  }
}
