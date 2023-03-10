package com.pharmasante.pharmasanteProyect.services.impl;

import com.pharmasante.pharmasanteProyect.models.Pedido;
import com.pharmasante.pharmasanteProyect.repository.IPedidoRepository;
import com.pharmasante.pharmasanteProyect.services.IPedidosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoServiceimpl implements IPedidosService {
    @Autowired
    IPedidoRepository pedidoRepository;

    @Override
    public Pedido guardar(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }
}
