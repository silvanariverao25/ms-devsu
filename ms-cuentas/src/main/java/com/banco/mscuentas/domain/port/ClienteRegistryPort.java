package com.banco.mscuentas.domain.port;

import java.util.UUID;

public interface ClienteRegistryPort {
  boolean exists(UUID clienteId);
  Short getEstadoOrNull(UUID clienteId);
}
