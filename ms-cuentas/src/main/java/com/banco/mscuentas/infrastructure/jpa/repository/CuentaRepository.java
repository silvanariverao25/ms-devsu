// CuentaRepository.java
package com.banco.mscuentas.infrastructure.jpa.repository;

import com.banco.mscuentas.infrastructure.jpa.entity.CuentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface CuentaRepository extends JpaRepository<CuentaEntity, java.util.UUID> {
  Optional<CuentaEntity> findByNumeroCuenta(String numeroCuenta);
}
