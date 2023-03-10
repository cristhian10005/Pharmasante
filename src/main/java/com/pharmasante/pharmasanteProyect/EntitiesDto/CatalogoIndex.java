package com.pharmasante.pharmasanteProyect.EntitiesDto;

import com.pharmasante.pharmasanteProyect.models.Producto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class CatalogoIndex {
    private List<Producto> masVendidos;
    private List<Producto> mejorValorados;

}
