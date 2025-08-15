package com.banco.msclientes.domain.model;

import java.util.UUID;

public class Persona {
    private UUID personaId;
    private String nombre;
    private String genero;
    private short edad;
    private String identificacion;
    private String direccion;
    private String telefono;

    // Getters/Setters
    public UUID getPersonaId() { return personaId; }
    public void setPersonaId(UUID personaId) { this.personaId = personaId; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
    public short getEdad() { return edad; }
    public void setEdad(short edad) { this.edad = edad; }
    public String getIdentificacion() { return identificacion; }
    public void setIdentificacion(String identificacion) { this.identificacion = identificacion; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}
