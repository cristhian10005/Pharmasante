package com.pharmasante.pharmasanteProyect.repository;

import com.pharmasante.pharmasanteProyect.EntitiesDto.PedidosDto;
import com.pharmasante.pharmasanteProyect.models.EstadoPedido;
import com.pharmasante.pharmasanteProyect.models.Pedido;
import com.pharmasante.pharmasanteProyect.models.TipoPedido;
import com.pharmasante.pharmasanteProyect.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPedidoRepository extends JpaRepository<Pedido, Integer> {
    List<Pedido> findByUsuario(Usuario usuario);

    List<Pedido> findByUsuarioAndEstado(Usuario usuario, EstadoPedido estadoPedido);

    List<Pedido> findByUsuarioAndTipoPedido(Usuario usuario, TipoPedido tipoPedido);
}
