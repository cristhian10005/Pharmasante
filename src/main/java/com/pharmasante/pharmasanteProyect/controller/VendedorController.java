package com.pharmasante.pharmasanteProyect.controller;

import com.pharmasante.pharmasanteProyect.models.Pedido;
import com.pharmasante.pharmasanteProyect.services.IPedidosVendedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/vendedor")
public class VendedorController {
    @Autowired
    IPedidosVendedorService pedidosVendedorService;

    @GetMapping
    public List<Pedido> listaPedidos(){
        return pedidosVendedorService.listaPedidos();
    }
}
