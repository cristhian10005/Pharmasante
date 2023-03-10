package com.pharmasante.pharmasanteProyect.services;

import com.pharmasante.pharmasanteProyect.models.Producto;
import com.pharmasante.pharmasanteProyect.EntitiesDto.ProductoDTO;
import org.springframework.validation.Errors;

import java.util.List;

public interface IProductoService {
    public List<Producto> listaProductos();
    Producto guardarProducto(ProductoDTO productoDTO, Errors errors);
    Producto actualizarProducto(ProductoDTO productoDTO, Errors errors);
    void eliminarProducto(int id);

    Producto buscarProducto(int id);
}
