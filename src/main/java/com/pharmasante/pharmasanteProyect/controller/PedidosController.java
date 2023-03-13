package com.pharmasante.pharmasanteProyect.controller;

import com.pharmasante.pharmasanteProyect.EntitiesDto.ClienteSalidaDto;
import com.pharmasante.pharmasanteProyect.EntitiesDto.IDto.IAccionRol;
import com.pharmasante.pharmasanteProyect.EntitiesDto.UsuarioEntradaDto;
import com.pharmasante.pharmasanteProyect.models.Pedido;
import com.pharmasante.pharmasanteProyect.models.Producto;
import com.pharmasante.pharmasanteProyect.services.IPedidosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidosController {
    @Autowired
    IPedidosService pedidosService;
    @Autowired
    @Qualifier("cliente")
    IAccionRol accionRol;

    @PostMapping("/carritolist")
    public ClienteSalidaDto listaCarroto(@RequestBody UsuarioEntradaDto usuario){
        ClienteSalidaDto cliente =(ClienteSalidaDto)accionRol.retornoSencillo(usuario.getIdCliente());
        cliente.setPedido(pedidosService.listaCarrito(usuario.getIdCliente()));
        return cliente;
    }
    @PostMapping("/carritoadd")
    public void agregarACarrito(@RequestBody UsuarioEntradaDto usuario){
         pedidosService.pedidoInicial( usuario.getIdServicio(),usuario.getIdCliente());
    }
    @PutMapping("/carritound")
    public void carritoUndidades(@RequestBody UsuarioEntradaDto usuario){
        pedidosService.adicionarUnidades(usuario.getIdCliente(), usuario.getBusqueda());
    }

    @DeleteMapping("/carritodel")
    public void eliminarDeCarrito(@RequestBody UsuarioEntradaDto usuario){
        pedidosService.eliminarProductoCarrito(usuario.getIdServicio());
    }

}
