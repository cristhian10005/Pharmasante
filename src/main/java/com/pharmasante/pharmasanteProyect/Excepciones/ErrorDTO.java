package com.pharmasante.pharmasanteProyect.Excepciones;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDTO {
    private String type;
    private String message;
}
