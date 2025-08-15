package com.banco.mscuentas.infrastructure.messaging;

import com.banco.mscuentas.domain.port.ClienteRegistryPort;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryClienteRegistry implements ClienteRegistryPort {
  private final ConcurrentHashMap<UUID, Short> clientes = new ConcurrentHashMap<>();

  @Override public boolean exists(UUID id){ return clientes.containsKey(id); }
  @Override public Short getEstadoOrNull(UUID id){ return clientes.get(id); }

  public void put(UUID id, short estado){ clientes.put(id, estado); }
  public void remove(UUID id){ clientes.remove(id); }
}
