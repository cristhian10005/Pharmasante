package com.pharmasante.pharmasanteProyect.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rol {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Integer id;

    @Column(name = "descripsion_rol")
    private String descripcion;

}
