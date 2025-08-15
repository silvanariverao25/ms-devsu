package com.banco.msclientes.application;

import com.banco.msclientes.api.dto.ClienteResponse;
import com.banco.msclientes.infrastructure.jpa.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetClienteUseCase {
    private final ClienteRepository repo;
    public GetClienteUseCase(ClienteRepository repo) { this.repo = repo; }

    public ClienteResponse handle(UUID personaId) {
        var e = repo.findById(personaId).orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        return new ClienteResponse(
                e.getPersonaId(), e.getClienteId(), e.getNombre(), e.getGenero(), e.getEdad(),
                e.getIdentificacion(), e.getDireccion(), e.getTelefono(), e.getEstado()
        );
    }
}
