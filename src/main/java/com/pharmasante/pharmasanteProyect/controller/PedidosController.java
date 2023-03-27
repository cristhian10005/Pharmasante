package com.pharmasante.pharmasanteProyect.controller;

import com.pharmasante.pharmasanteProyect.EntitiesDto.*;
import com.pharmasante.pharmasanteProyect.EntitiesDto.IDto.IAccionRol;
import com.pharmasante.pharmasanteProyect.services.IPedidosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

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
         pedidosService.pedidoInicial(usuario.getIdServicio(),usuario.getIdCliente(), 1);
    }
    @PutMapping("/carritound")
    public void carritoUndidades(@RequestBody UsuarioEntradaDto usuario){
        pedidosService.adicionarUnidades(usuario.getIdServicio(), usuario.getBusqueda());
    }

    @DeleteMapping("/carritodel")
    public void eliminarDeCarrito(@RequestBody UsuarioEntradaDto usuario){
        pedidosService.eliminarProductoCarrito(usuario.getIdServicio());
    }

    //Solicitud de pedidos

    @PostMapping("/recoger")
    public void enviarRecogerTienda(@RequestBody UsuarioEntradaDto usuario){
        pedidosService.solicituTienda(usuario.getIdServicio());
    }

    @PostMapping("/domicilio")
    public void enviarDomicilio(@Valid @RequestBody DomicilioDto domicilio, Errors errors){
        pedidosService.solicitudDomicilio(domicilio, errors);
    }

    @PostMapping("/listapedido")
    public PedidosDto listaPedidos(@RequestBody UsuarioEntradaDto usuario){
        return pedidosService.listaPedidos(usuario.getIdCliente());
    }
    @DeleteMapping("/eliminarPedido")
    public void elimininarPedido(@RequestBody UsuarioEntradaDto usuario){
        pedidosService.eliminarPedido(usuario.getIdServicio());
    }

    @PostMapping ("/calificar")
    public void calificarProducto(@RequestBody CalificacionDto calificacion){
        pedidosService.calificar(calificacion);
    }

}
