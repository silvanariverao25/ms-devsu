package com.banco.msclientes.api.dto;

import java.util.UUID;

public record ClienteResponse(
        UUID personaId,
        UUID clienteId,
        String nombre,
        String genero,
        short edad,
        String identificacion,
        String direccion,
        String telefono,
        short estado
) {}
