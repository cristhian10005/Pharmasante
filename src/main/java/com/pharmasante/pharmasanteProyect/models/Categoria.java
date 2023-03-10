package com.pharmasante.pharmasanteProyect.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "categoriaproducto")
@AllArgsConstructor
@NoArgsConstructor
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Integer codCategoria;

    @Column(name = "descripsion_categoria")
    private String descripcion;
}
