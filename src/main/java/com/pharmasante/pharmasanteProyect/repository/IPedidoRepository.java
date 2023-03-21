package com.pharmasante.pharmasanteProyect.repository;

import com.pharmasante.pharmasanteProyect.EntitiesDto.PedidosDto;
import com.pharmasante.pharmasanteProyect.models.EstadoPedido;
import com.pharmasante.pharmasanteProyect.models.Pedido;
import com.pharmasante.pharmasanteProyect.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPedidoRepository extends JpaRepository<Pedido, Integer> {
    List<Pedido> findByUsuarioAndEstadoIsNotOrderByIdDesc(Usuario usuario, EstadoPedido estado);

    List<Pedido> findByUsuarioAndEstado(Usuario usuario, EstadoPedido estadoPedido);
}
