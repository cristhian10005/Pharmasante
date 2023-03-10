package com.pharmasante.pharmasanteProyect.controller;

import com.pharmasante.pharmasanteProyect.EntitiesDto.CatalogoIndex;
import com.pharmasante.pharmasanteProyect.models.Producto;
import com.pharmasante.pharmasanteProyect.services.ICatalogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
        @RequestMapping("/catalogo")
public class CatalogoController {
    @Autowired
    ICatalogoService catalogoService;

    @GetMapping("/index")
    public CatalogoIndex listaCatalogo(){
        return catalogoService.listaPrincipal();
    }
    @GetMapping("/filtro/{id}")
    public List<Producto>listaPorFiltro(@PathVariable int id){
        return catalogoService.filtroPorCategoriaId(id);
    }

    @GetMapping("busqueda/{nombre}")
    public List<Producto> listaPorNombre(@PathVariable String nombre){
        return catalogoService.buscarProducto(nombre);
    }


}
