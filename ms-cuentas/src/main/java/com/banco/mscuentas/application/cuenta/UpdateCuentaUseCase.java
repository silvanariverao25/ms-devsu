// UpdateCuentaUseCase.java
package com.banco.mscuentas.application.cuenta;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banco.mscuentas.api.dto.CuentaResponse;
import com.banco.mscuentas.api.dto.CuentaUpdateRequest;
import com.banco.mscuentas.infrastructure.jpa.repository.CuentaRepository;

@Service
public class UpdateCuentaUseCase {
  private final CuentaRepository repo;
  public UpdateCuentaUseCase(CuentaRepository r){ this.repo=r; }

  @Transactional
  public CuentaResponse handle(UUID id, CuentaUpdateRequest req){
    var e = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada"));
    e.setTipoCuenta(req.tipoCuenta());
    e.setEstado(req.estado());
    var saved = repo.save(e);
    return new CuentaResponse(saved.getCuentaId(), saved.getClienteId(), saved.getNumeroCuenta(), saved.getTipoCuenta(), saved.getSaldoInicial(), saved.getEstado());
  }
}
