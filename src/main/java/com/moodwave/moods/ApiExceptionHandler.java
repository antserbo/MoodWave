package com.moodwave.moods;

import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidation(MethodArgumentNotValidException ex) {
        var msg = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + " " + err.getDefaultMessage())
                .findFirst().orElse("validation failed");
        return ResponseEntity.badRequest().body("Invalid request: " + msg);
    }
}
