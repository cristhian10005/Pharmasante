package com.pharmasante.pharmasanteProyect.repository;

import com.pharmasante.pharmasanteProyect.models.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface IDetallePedidoRepository extends JpaRepository<DetallePedido, Integer> {
    //@Query(value = "call Registro_pedido(:idProducto, :idUsuario, :fecha, :tipoPedido)",
      //      nativeQuery = true)
     //void carritoCompras(int idProducto, int idUsuario, LocalDate fecha, int TipoPedido);
}
