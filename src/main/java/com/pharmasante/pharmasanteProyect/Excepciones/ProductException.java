package com.pharmasante.pharmasanteProyect.Excepciones;

import lombok.Data;

@Data
public class ProductException extends RuntimeException{
    private String type;
    public ProductException(String message, String type) {
        super(message);
        this.type = type;
    }
}
