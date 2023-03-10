package com.pharmasante.pharmasanteProyect.repository;

import com.pharmasante.pharmasanteProyect.models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPedidoRepository extends JpaRepository<Pedido, Integer> {
}
