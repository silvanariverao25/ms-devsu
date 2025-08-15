package com.banco.mscuentas.api.error;

import java.time.OffsetDateTime;
import java.util.Map;

public record ApiError(
        String message,
        int status,
        OffsetDateTime timestamp,
        Map<String, String> fieldErrors // para validaciones @Valid
) {}
