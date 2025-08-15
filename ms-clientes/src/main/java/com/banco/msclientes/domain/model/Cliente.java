package com.banco.msclientes.domain.model;

import java.util.UUID;

public class Cliente extends Persona {
    private UUID clienteId;
    private String passwordHash;
    private short estado;

    public UUID getClienteId() { return clienteId; }
    public void setClienteId(UUID clienteId) { this.clienteId = clienteId; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public short getEstado() { return estado; }
    public void setEstado(short estado) { this.estado = estado; }
}
