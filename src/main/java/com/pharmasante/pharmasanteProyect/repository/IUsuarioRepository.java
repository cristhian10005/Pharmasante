package com.pharmasante.pharmasanteProyect.repository;
import com.pharmasante.pharmasanteProyect.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {
}
