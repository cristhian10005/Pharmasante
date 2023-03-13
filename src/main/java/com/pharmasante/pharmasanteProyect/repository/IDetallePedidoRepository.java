package com.pharmasante.pharmasanteProyect.repository;

import com.pharmasante.pharmasanteProyect.models.DetallePedido;
import com.pharmasante.pharmasanteProyect.models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface IDetallePedidoRepository extends JpaRepository<DetallePedido, Integer> {
    @Transactional
    @Modifying
    @Query(value = "{call Registro_pedido(:idProducto, :idUsuario, :fecha, :tipoPedido)}",
            nativeQuery = true)
    void carritoCompras(@Param("idProducto") int idProducto, @Param("idUsuario") int idUsuario,
                                    @Param("fecha") LocalDate fecha, @Param("tipoPedido") int tipoPedido);
    List<DetallePedido> findByPedido(Pedido pedido);
}
