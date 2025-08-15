package com.banco.msclientes.api.controller;

import com.banco.msclientes.api.dto.*;
import com.banco.msclientes.application.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final CreateClienteUseCase createUC;
    private final GetClienteUseCase getUC;
    private final ListClientesUseCase listUC;
    private final UpdateClienteUseCase updateUC;
    private final DeleteClienteUseCase deleteUC;

    public ClienteController(CreateClienteUseCase createUC,
                             GetClienteUseCase getUC,
                             ListClientesUseCase listUC,
                             UpdateClienteUseCase updateUC,
                             DeleteClienteUseCase deleteUC) {
        this.createUC = createUC;
        this.getUC = getUC;
        this.listUC = listUC;
        this.updateUC = updateUC;
        this.deleteUC = deleteUC;
    }

    @GetMapping
    public Page<ClienteResponse> list(Pageable pageable) {
        return listUC.handle(pageable);
    }

    @GetMapping("/{personaId}")
    public ClienteResponse get(@PathVariable UUID personaId) {
        return getUC.handle(personaId);
    }

    @PostMapping
    public ResponseEntity<ClienteResponse> create(@Valid @RequestBody ClienteCreateRequest req) {
        var res = createUC.handle(req);
        return ResponseEntity.created(URI.create("/clientes/" + res.personaId())).body(res);
    }

    @PutMapping("/{personaId}")
    public ClienteResponse update(@PathVariable UUID personaId, @Valid @RequestBody ClienteUpdateRequest req) {
        return updateUC.handle(personaId, req);
    }

    @DeleteMapping("/{personaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID personaId) {
        deleteUC.handle(personaId);
    }
}
