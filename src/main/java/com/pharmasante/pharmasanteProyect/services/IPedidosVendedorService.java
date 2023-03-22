package com.pharmasante.pharmasanteProyect.services;

import com.pharmasante.pharmasanteProyect.EntitiesDto.CalificacionDto;
import com.pharmasante.pharmasanteProyect.EntitiesDto.DomicilioDto;
import com.pharmasante.pharmasanteProyect.EntitiesDto.PedidosDto;
import com.pharmasante.pharmasanteProyect.models.Pedido;
import org.springframework.validation.Errors;

import java.util.List;

public interface IPedidosVendedorService {
    List<Pedido> listaPedidos();
    public void eliminarPedido(int idPedido);
    public void calificar(CalificacionDto calificacion);
}
