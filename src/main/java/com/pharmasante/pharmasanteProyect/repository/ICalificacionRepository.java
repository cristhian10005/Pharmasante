package com.pharmasante.pharmasanteProyect.repository;

import com.pharmasante.pharmasanteProyect.models.Calificacion;
import com.pharmasante.pharmasanteProyect.models.Producto;
import com.pharmasante.pharmasanteProyect.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICalificacionRepository extends JpaRepository<Calificacion, Integer> {
    List<Calificacion> findByProducto(Producto producto);
    List<Calificacion> findByProductoAndUsuario(Producto producto, Usuario usuario);
}
