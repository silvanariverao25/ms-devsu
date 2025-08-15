package com.banco.msclientes.infrastructure.jpa.repository;

import com.banco.msclientes.infrastructure.jpa.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClienteRepository extends JpaRepository<ClienteEntity, UUID> {
    // El campo identificacion está en la superclase PersonaEntity
    Optional<ClienteEntity> findByIdentificacion(String identificacion);
    Optional<ClienteEntity> findByClienteId(UUID clienteId);
}
