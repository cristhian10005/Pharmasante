package com.pharmasante.pharmasanteProyect.controller;

import com.pharmasante.pharmasanteProyect.EntitiesDto.CatalogoIndex;
import com.pharmasante.pharmasanteProyect.EntitiesDto.UsuarioEntradaDto;
import com.pharmasante.pharmasanteProyect.EntitiesDto.ClienteSalidaDto;
import com.pharmasante.pharmasanteProyect.EntitiesDto.IDto.IAccionRol;
import com.pharmasante.pharmasanteProyect.models.Producto;
import com.pharmasante.pharmasanteProyect.services.ICatalogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalogo")
public class CatalogoController {
    @Autowired
    ICatalogoService catalogoService;
    @Autowired
    @Qualifier("cliente")
    IAccionRol accionRol;

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

    //Cliente "autencicado"

    @PostMapping("/cliente")
    public ClienteSalidaDto listaPrincipalCliente(@RequestBody UsuarioEntradaDto cliente){
        ClienteSalidaDto clienteDto = (ClienteSalidaDto)accionRol.retornoSencillo(cliente.getIdCliente());
        clienteDto.setCatalogoIndex(catalogoService.listaPrincipal());
        return clienteDto;
    }

    @PostMapping("/filtrocl")
    public ClienteSalidaDto listaFiltroCliente(@RequestBody UsuarioEntradaDto cliente){
        ClienteSalidaDto clienteDto = (ClienteSalidaDto)accionRol.retornoSencillo(cliente.getIdCliente());
        clienteDto.setListaProducto(catalogoService.filtroPorCategoriaId(cliente.getIdServicio()));
        return clienteDto;
    }

    @PostMapping("/busquedacl")
    public ClienteSalidaDto listaBusquedaCliente(@RequestBody UsuarioEntradaDto cliente){
        ClienteSalidaDto clienteDto = (ClienteSalidaDto)accionRol.retornoSencillo(cliente.getIdCliente());
        clienteDto.setListaProducto(catalogoService.buscarProducto(cliente.getBusqueda()));
        return clienteDto;
    }


}
