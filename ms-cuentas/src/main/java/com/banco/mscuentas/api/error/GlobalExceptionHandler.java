package com.banco.mscuentas.api.error;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.http.converter.HttpMessageNotReadableException;

import jakarta.validation.ConstraintViolationException;
import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 400 - reglas de negocio simples
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgument(IllegalArgumentException ex) {
        var body = new ApiError(ex.getMessage(), HttpStatus.BAD_REQUEST.value(),
                OffsetDateTime.now(), Map.of());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    // 404 - recurso no encontrado
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiError> handleNotFound(NoSuchElementException ex) {
        var body = new ApiError(ex.getMessage(), HttpStatus.NOT_FOUND.value(),
                OffsetDateTime.now(), Map.of());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    // 409 - unicidad / FK (DB)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleConflict(DataIntegrityViolationException ex) {
        var body = new ApiError("Conflicto con los datos (restricción de base de datos)",
                HttpStatus.CONFLICT.value(), OffsetDateTime.now(), Map.of());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    // 422 - validación de @Valid en body (DTOs)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(f -> errors.put(f.getField(), f.getDefaultMessage()));

        var body = new ApiError("Validación fallida",
                HttpStatus.UNPROCESSABLE_ENTITY.value(), OffsetDateTime.now(), errors);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(body);
    }

    // 400 - validaciones en @RequestParam / @PathVariable (por @Validated)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> errors = new LinkedHashMap<>();
        ex.getConstraintViolations().forEach(v ->
                errors.put(v.getPropertyPath().toString(), v.getMessage()));
        var body = new ApiError("Parámetros inválidos",
                HttpStatus.BAD_REQUEST.value(), OffsetDateTime.now(), errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    // 400 - falta un parámetro requerido (?numero=...)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiError> handleMissingRequestParam(MissingServletRequestParameterException ex) {
        var body = new ApiError(
                "Falta parámetro requerido: " + ex.getParameterName(),
                HttpStatus.BAD_REQUEST.value(),
                OffsetDateTime.now(),
                Map.of(ex.getParameterName(), "requerido")
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    // 400 - tipo incorrecto en path/params (ej. UUID inválido)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String name = ex.getName();
        String required = (ex.getRequiredType() != null) ? ex.getRequiredType().getSimpleName() : "valor válido";
        var body = new ApiError(
                "Parámetro '" + name + "' con formato inválido (se esperaba " + required + ")",
                HttpStatus.BAD_REQUEST.value(),
                OffsetDateTime.now(),
                Map.of(name, "formato inválido")
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    // 400 - JSON malformado / tipos incompatibles en el body
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleUnreadable(HttpMessageNotReadableException ex) {
        var body = new ApiError(
                "Cuerpo de la petición inválido o malformado",
                HttpStatus.BAD_REQUEST.value(),
                OffsetDateTime.now(),
                Map.of()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    // 500 - fallback
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex) {
        var body = new ApiError("Error interno del servidor",
                HttpStatus.INTERNAL_SERVER_ERROR.value(), OffsetDateTime.now(), Map.of());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
