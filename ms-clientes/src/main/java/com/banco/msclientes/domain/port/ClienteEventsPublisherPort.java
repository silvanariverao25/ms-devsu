package com.banco.msclientes.domain.port;

import java.util.UUID;

public interface ClienteEventsPublisherPort {
    void publishCreated(UUID clienteId, String identificacion, short estado);
    void publishUpdated(UUID clienteId, String identificacion, short estado);
    void publishDeleted(UUID clienteId);
}
