package com.banco.msclientes.application;

import com.banco.msclientes.domain.port.ClienteEventsPublisherPort;
import com.banco.msclientes.infrastructure.jpa.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class DeleteClienteUseCase {
    private final ClienteRepository repo;
    private final ClienteEventsPublisherPort publisher;

    public DeleteClienteUseCase(ClienteRepository repo, ClienteEventsPublisherPort publisher) {
        this.repo = repo; this.publisher = publisher;
    }

    @Transactional
    public void handle(UUID personaId) {
        var e = repo.findById(personaId).orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        var clienteId = e.getClienteId();
        repo.delete(e);
        publisher.publishDeleted(clienteId);
    }
}
