package com.banco.msclientes.application;

import com.banco.msclientes.api.dto.ClienteCreateRequest;
import com.banco.msclientes.api.dto.ClienteResponse;
import com.banco.msclientes.domain.port.ClienteEventsPublisherPort;
import com.banco.msclientes.infrastructure.jpa.entity.ClienteEntity;
import com.banco.msclientes.infrastructure.jpa.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateClienteUseCase {
    private final ClienteRepository repo;
    private final ClienteEventsPublisherPort publisher;

    public CreateClienteUseCase(ClienteRepository repo, ClienteEventsPublisherPort publisher) {
        this.repo = repo; this.publisher = publisher;
    }

    @Transactional
    public ClienteResponse handle(ClienteCreateRequest req) {
        repo.findByIdentificacion(req.identificacion()).ifPresent(x -> {
            throw new IllegalArgumentException("Identificación ya existe");
        });

        var e = new ClienteEntity();
        e.setNombre(req.nombre());
        e.setGenero(req.genero());
        e.setEdad(req.edad());
        e.setIdentificacion(req.identificacion());
        e.setDireccion(req.direccion());
        e.setTelefono(req.telefono());
        e.setPasswordHash(req.passwordHash());
        e.setEstado(req.estado());

        var saved = repo.save(e);
        publisher.publishCreated(saved.getClienteId(), saved.getIdentificacion(), saved.getEstado());

        return new ClienteResponse(
                saved.getPersonaId(), saved.getClienteId(), saved.getNombre(), saved.getGenero(),
                saved.getEdad(), saved.getIdentificacion(), saved.getDireccion(), saved.getTelefono(), saved.getEstado()
        );
    }
}
