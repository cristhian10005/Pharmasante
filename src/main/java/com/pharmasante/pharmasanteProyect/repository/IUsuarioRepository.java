package com.pharmasante.pharmasanteProyect.repository;
import com.pharmasante.pharmasanteProyect.models.Rol;
import com.pharmasante.pharmasanteProyect.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {
    List<Usuario> findByNombreUsuarioAndPassword(String usuario, String pass);
    List<Usuario> findByRol(Rol rol);
}
