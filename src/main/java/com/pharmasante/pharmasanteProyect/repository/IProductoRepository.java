package com.pharmasante.pharmasanteProyect.repository;

import com.pharmasante.pharmasanteProyect.models.Categoria;
import com.pharmasante.pharmasanteProyect.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IProductoRepository extends JpaRepository<Producto, Integer> {
    @Query(
            value = "SELECT * FROM Producto ORDER BY unidades_vendidas DESC LIMIT 5",
            nativeQuery = true
    )
     List<Producto> catalogoVendidos();

    @Query(
            value = "SELECT * FROM Producto ORDER BY calificacion DESC LIMIT 5",
            nativeQuery = true
    )
     List<Producto> catalogoCalificados();

     List<Producto> findByCategoria(Categoria categoria);

    @Query("SELECT p FROM Producto p JOIN p.proveedor pr WHERE LOWER(pr.nombre) LIKE CONCAT('%', LOWER(:nombre), '%') OR " +
            "LOWER(p.nombre) LIKE CONCAT('%', LOWER(:nombre), '%')")
    List<Producto> busqueda(@Param("nombre") String nombre);


}
