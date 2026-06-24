package com.pagos.microservicio.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice // Le dice a Spring Boot que esta clase interceptará cualquier error en los endpoints
public class GlobalExceptionHandler {

    // Este método captura específicamente los errores de validación del @Valid (Bean Validation)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();

        // Recorremos cada campo que falló en la validación y extraemos su mensaje personalizado
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String campo = ((FieldError) error).getField();
            String mensaje = error.getDefaultMessage();
            errores.put(campo, mensaje);
        });

        // Retornamos un JSON estructurado con los errores limpitos y un HTTP 400 Bad Request
        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }
}