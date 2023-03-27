package com.pharmasante.pharmasanteProyect.repository;

import com.pharmasante.pharmasanteProyect.models.Cliente;
import com.pharmasante.pharmasanteProyect.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IClienteRepository extends JpaRepository<Cliente, Integer> {
    List<Cliente> findByUsuario(Usuario usuario);
}
