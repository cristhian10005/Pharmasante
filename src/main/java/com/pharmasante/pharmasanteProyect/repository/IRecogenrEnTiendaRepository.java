package com.pharmasante.pharmasanteProyect.repository;


import com.pharmasante.pharmasanteProyect.models.Pedido;
import com.pharmasante.pharmasanteProyect.models.RecogerEnTienda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRecogenrEnTiendaRepository extends JpaRepository<RecogerEnTienda, Integer> {
    List<RecogerEnTienda> findByPedido(Pedido pedido);
}
