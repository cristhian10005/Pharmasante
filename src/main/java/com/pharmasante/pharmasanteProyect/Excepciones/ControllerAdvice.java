package com.pharmasante.pharmasanteProyect.Excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(value = ProductException.class)
    public ResponseEntity<ErrorDTO> runtimeExceptionHandler(ProductException ptoEx) {
        ErrorDTO errorDTO = new ErrorDTO(ptoEx.getType(), ptoEx.getMessage());
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }
}
