package com.banco.mscuentas.application.cuenta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.banco.mscuentas.api.dto.CuentaResponse;
import com.banco.mscuentas.infrastructure.jpa.repository.CuentaRepository;

@Service
public class ListCuentasUseCase {
    private final CuentaRepository repo;
    public ListCuentasUseCase(CuentaRepository repo) { this.repo = repo; }

    public Page<CuentaResponse> handle(Pageable p) {
        return repo.findAll(p).map(e -> new CuentaResponse(
        		e.getCuentaId(), e.getClienteId(), e.getNumeroCuenta(), e.getTipoCuenta(),
        		e.getSaldoInicial(), e.getEstado()
        ));
    }
}
