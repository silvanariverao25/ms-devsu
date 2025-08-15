package com.banco.msclientes.infrastructure.jpa.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "persona", schema = "bank")
@Inheritance(strategy = InheritanceType.JOINED)
public class PersonaEntity {

    @Id
    @GeneratedValue
    @Column(name = "persona_id", columnDefinition = "UUID")
    private UUID personaId;

    @Column(nullable = false, length = 120)
    private String nombre;

    @Column(nullable = false, length = 20)
    private String genero;

    @Column(nullable = false)
    private short edad;

    @Column(nullable = false, length = 50, unique = true)
    private String identificacion;

    @Column(nullable = false, length = 200)
    private String direccion;

    @Column(nullable = false, length = 30)
    private String telefono;

    // Getters y setters
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
