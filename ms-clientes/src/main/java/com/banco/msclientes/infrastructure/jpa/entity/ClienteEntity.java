package com.banco.msclientes.infrastructure.jpa.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "cliente", schema = "bank")
@PrimaryKeyJoinColumn(name = "persona_id") // PK compartida con persona
public class ClienteEntity extends PersonaEntity {

    // Campo adicional propio del cliente (no PK).
    @Column(name = "cliente_id", columnDefinition = "UUID", nullable = false, unique = true)
    private UUID clienteId = java.util.UUID.randomUUID();

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(name = "estado", nullable = false)
    private short estado; // 1=activo, 0=inactivo

    // Getters y setters
    public UUID getClienteId() { return clienteId; }
    public void setClienteId(UUID clienteId) { this.clienteId = clienteId; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public short getEstado() { return estado; }
    public void setEstado(short estado) { this.estado = estado; }
}
