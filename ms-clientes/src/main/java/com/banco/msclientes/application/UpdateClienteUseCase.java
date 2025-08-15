package com.banco.msclientes.application;

import com.banco.msclientes.api.dto.ClienteResponse;
import com.banco.msclientes.api.dto.ClienteUpdateRequest;
import com.banco.msclientes.domain.port.ClienteEventsPublisherPort;
import com.banco.msclientes.infrastructure.jpa.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UpdateClienteUseCase {
    private final ClienteRepository repo;
    private final ClienteEventsPublisherPort publisher;

    public UpdateClienteUseCase(ClienteRepository repo, ClienteEventsPublisherPort publisher) {
        this.repo = repo; this.publisher = publisher;
    }

    @Transactional
    public ClienteResponse handle(UUID personaId, ClienteUpdateRequest req) {
        var e = repo.findById(personaId).orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        e.setNombre(req.nombre());
        e.setGenero(req.genero());
        e.setEdad(req.edad());
        e.setDireccion(req.direccion());
        e.setTelefono(req.telefono());
        e.setEstado(req.estado());
        var saved = repo.save(e);

        publisher.publishUpdated(saved.getClienteId(), saved.getIdentificacion(), saved.getEstado());

        return new ClienteResponse(
                saved.getPersonaId(), saved.getClienteId(), saved.getNombre(), saved.getGenero(), saved.getEdad(),
                saved.getIdentificacion(), saved.getDireccion(), saved.getTelefono(), saved.getEstado()
        );
    }
}
