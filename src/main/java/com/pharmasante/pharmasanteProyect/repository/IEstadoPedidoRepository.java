package com.pharmasante.pharmasanteProyect.repository;

import com.pharmasante.pharmasanteProyect.models.EstadoPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEstadoPedidoRepository extends JpaRepository<EstadoPedido, Integer> {
}
