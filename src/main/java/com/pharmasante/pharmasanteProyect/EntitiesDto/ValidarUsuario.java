package com.pharmasante.pharmasanteProyect.EntitiesDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ValidarUsuario {
    private String usuario;
    private String pass;
}
