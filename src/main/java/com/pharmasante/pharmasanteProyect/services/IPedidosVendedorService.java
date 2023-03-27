package com.pharmasante.pharmasanteProyect.services;

import com.pharmasante.pharmasanteProyect.EntitiesDto.CalificacionDto;
import com.pharmasante.pharmasanteProyect.EntitiesDto.DomicilioDto;
import com.pharmasante.pharmasanteProyect.EntitiesDto.PedidosDto;
import com.pharmasante.pharmasanteProyect.models.Pedido;
import org.springframework.validation.Errors;

import java.time.LocalTime;
import java.util.List;

public interface IPedidosVendedorService {
    List<Pedido> listaPedidos();
    public void AsignarEstado(int idPedido, int estado, LocalTime hora, int idUsuario);

}
