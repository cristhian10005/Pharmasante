package com.pharmasante.pharmasanteProyect.services;

import com.pharmasante.pharmasanteProyect.EntitiesDto.DomicilioDto;
import com.pharmasante.pharmasanteProyect.EntitiesDto.UsuarioEntradaDto;
import com.pharmasante.pharmasanteProyect.models.Pedido;
import org.springframework.validation.Errors;

public interface IPedidosService {
     void pedidoInicial(int idProducto, int idUsuario);
     Pedido listaCarrito(int idUsuario);
      void adicionarUnidades(int idDetalle, String tipo);
      void eliminarProductoCarrito(int idDetalle);
      void solicituTienda(int idPedido);
      void solicitudDomicilio(DomicilioDto domicilio, Errors errors);
}
