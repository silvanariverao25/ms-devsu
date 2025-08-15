package com.banco.msclientes.application;

import com.banco.msclientes.api.dto.ClienteResponse;
import com.banco.msclientes.infrastructure.jpa.repository.ClienteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListClientesUseCase {
    private final ClienteRepository repo;
    public ListClientesUseCase(ClienteRepository repo) { this.repo = repo; }

    public Page<ClienteResponse> handle(Pageable p) {
        return repo.findAll(p).map(e -> new ClienteResponse(
                e.getPersonaId(), e.getClienteId(), e.getNombre(), e.getGenero(), e.getEdad(),
                e.getIdentificacion(), e.getDireccion(), e.getTelefono(), e.getEstado()
        ));
    }
}
