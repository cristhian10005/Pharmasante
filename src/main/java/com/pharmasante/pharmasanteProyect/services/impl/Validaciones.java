package com.pharmasante.pharmasanteProyect.services.impl;

import com.pharmasante.pharmasanteProyect.Excepciones.ProductException;
import com.pharmasante.pharmasanteProyect.services.IValidaciones;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.Optional;
@Service
public class Validaciones implements IValidaciones {
    @Override
    public void validacionDeErrores(Errors errors){
        if (errors.getAllErrors() != null){
            Optional<String> mensaje = errors.getAllErrors().stream()
                    .map(objErr -> objErr.getDefaultMessage())
                    .findFirst();
            mensaje.ifPresent(m-> {
                throw new ProductException(m,"Campo mal diligenciado");
            });
        }
    }}
