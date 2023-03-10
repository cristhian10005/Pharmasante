package com.pharmasante.pharmasanteProyect.services;

import com.pharmasante.pharmasanteProyect.EntitiesDto.CatalogoIndex;
import com.pharmasante.pharmasanteProyect.models.Producto;

import java.util.List;

public interface ICatalogoService {
    CatalogoIndex listaPrincipal();
    List<Producto>filtroPorCategoriaId(int idCategoria);
    public List<Producto> buscarProducto(String nombre);
}
