package com.pharmasante.pharmasanteProyect.controller;

import com.pharmasante.pharmasanteProyect.models.Pedido;
import com.pharmasante.pharmasanteProyect.services.IPedidosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedidos")
public class PedidosController {
  /*  @Autowired
    IPedidosService pedidosService;
    @PostMapping("/carrito")
    Pedido agregarACarrito(@RequestBody Pedido p){
        return pedidosService.guardar(p);
    }*/
}
