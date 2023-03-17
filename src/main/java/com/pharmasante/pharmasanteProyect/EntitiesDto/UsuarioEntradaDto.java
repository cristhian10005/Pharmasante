package com.pharmasante.pharmasanteProyect.EntitiesDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioEntradaDto {
    private int idCliente;
    private String nombre;
    private int idServicio;
    private String busqueda;
    private String contacto;

}
