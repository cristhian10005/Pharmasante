package com.pharmasante.pharmasanteProyect.services;

import com.pharmasante.pharmasanteProyect.EntitiesDto.CalificacionDto;
import com.pharmasante.pharmasanteProyect.EntitiesDto.DomicilioDto;
import com.pharmasante.pharmasanteProyect.EntitiesDto.PedidosDto;
import com.pharmasante.pharmasanteProyect.models.Pedido;
import org.springframework.validation.Errors;

import java.util.Set;

public interface IPedidosService {
     void pedidoInicial(int idProducto, int idUsuario, int idEstado);
     Pedido listaCarrito(int idUsuario);
      void adicionarUnidades(int idDetalle, String tipo);
      void eliminarProductoCarrito(int idDetalle);
      void solicituTienda(int idPedido);
      void solicitudDomicilio(DomicilioDto domicilio, Errors errors);
    PedidosDto listaPedidos(int idUsuario);
    public void eliminarPedido(int idPedido);
    public void calificar(CalificacionDto calificacion);
}
