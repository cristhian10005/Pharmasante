package com.pharmasante.pharmasanteProyect.services.impl;

import com.pharmasante.pharmasanteProyect.EntitiesDto.CatalogoIndex;
import com.pharmasante.pharmasanteProyect.Excepciones.ProductException;
import com.pharmasante.pharmasanteProyect.models.Categoria;
import com.pharmasante.pharmasanteProyect.models.Producto;
import com.pharmasante.pharmasanteProyect.repository.ICategoriaRepository;
import com.pharmasante.pharmasanteProyect.repository.IProductoRepository;
import com.pharmasante.pharmasanteProyect.services.ICatalogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CatalogoServiceimpl implements ICatalogoService {

    @Autowired
    IProductoRepository productoRepository;
    @Autowired
    ICategoriaRepository categoriaRepository;

    @Override
    public CatalogoIndex listaPrincipal() {
        CatalogoIndex catalogo = new CatalogoIndex();
        catalogo.setMasVendidos(productoRepository.catalogoVendidos());
        catalogo.setMejorValorados(productoRepository.catalogoCalificados());
        return catalogo;
    }

    @Override
    public List<Producto> filtroPorCategoriaId(int idCategoria) {
        Optional<Categoria> categoria = categoriaRepository.findById(idCategoria);
        if (categoria.isPresent()){
            return filtroPorCategoria(categoria.get());
        }
        throw new ProductException("Categoría","Categoría no encontrada");
    }

    private List<Producto> filtroPorCategoria(Categoria categoria) {
        return productoRepository.findByCategoria(categoria);
    }
    public List<Producto> buscarProducto(String nombre){
        List<Producto> p= productoRepository.busqueda(nombre);
        if (!p.isEmpty()){
            return p;
        }
        throw new ProductException("Parámetor invalido","El catálogo no dipone de este producto: " + nombre);
    }

}

