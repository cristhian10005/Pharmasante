package com.pharmasante.pharmasanteProyect.repository;

import com.pharmasante.pharmasanteProyect.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClienteRepository extends JpaRepository<Cliente, Integer> {
}
