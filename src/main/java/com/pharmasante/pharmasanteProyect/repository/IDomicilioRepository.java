package com.pharmasante.pharmasanteProyect.repository;


import com.pharmasante.pharmasanteProyect.models.Domicilio;
import com.pharmasante.pharmasanteProyect.models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IDomicilioRepository extends JpaRepository<Domicilio, Integer> {
    List<Domicilio> findByPedido(Pedido pedido);
}
