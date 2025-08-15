// CreateCuentaUseCase.java
package com.banco.mscuentas.application.cuenta;

import com.banco.mscuentas.api.dto.*;
import com.banco.mscuentas.domain.port.ClienteRegistryPort;
import com.banco.mscuentas.infrastructure.jpa.entity.CuentaEntity;
import com.banco.mscuentas.infrastructure.jpa.repository.CuentaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateCuentaUseCase {
  private final CuentaRepository cuentaRepo;	
  private final ClienteRegistryPort clientes;

  public CreateCuentaUseCase(CuentaRepository c, ClienteRegistryPort cr){ this.cuentaRepo=c; this.clientes=cr; }

  @Transactional
  public CuentaResponse handle(CuentaCreateRequest req){
    if(!clientes.exists(req.clienteId())) throw new IllegalArgumentException("Cliente no existe (aún no recibido por eventos)");
    cuentaRepo.findByNumeroCuenta(req.numeroCuenta()).ifPresent(x -> { throw new IllegalArgumentException("Número de cuenta ya existe"); });

    var e = new CuentaEntity();
    e.setClienteId(req.clienteId());
    e.setNumeroCuenta(req.numeroCuenta());
    e.setTipoCuenta(req.tipoCuenta());
    e.setSaldoInicial(req.saldoInicial());
    e.setEstado(req.estado());
    var saved = cuentaRepo.save(e);

    return new CuentaResponse(saved.getCuentaId(), saved.getClienteId(), saved.getNumeroCuenta(), saved.getTipoCuenta(), saved.getSaldoInicial(), saved.getEstado());
  }
}
