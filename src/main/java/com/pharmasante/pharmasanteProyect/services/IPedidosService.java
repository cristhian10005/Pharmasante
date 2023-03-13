package com.pharmasante.pharmasanteProyect.services;

import com.pharmasante.pharmasanteProyect.models.Pedido;

public interface IPedidosService {
     void pedidoInicial(int idProducto, int idUsuario);
     Pedido listaCarrito(int idUsuario);
     public void adicionarUnidades(int idDetalle, String tipo);
     public void eliminarProductoCarrito(int idDetalle);
}
