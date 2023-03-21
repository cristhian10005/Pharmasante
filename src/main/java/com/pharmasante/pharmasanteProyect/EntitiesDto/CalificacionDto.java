package com.pharmasante.pharmasanteProyect.EntitiesDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CalificacionDto {
    private int calificacion;
    private int idProducto;
    private int idUsuario;
}
