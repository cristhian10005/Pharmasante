package com.pharmasante.pharmasanteProyect.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "proveedor")
@AllArgsConstructor
@NoArgsConstructor
public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proveedor")
    private Integer codProveedor;

    @Column(name = "nombre_proveedor")
    private String nombre;

    @Column(name = "numero_contacto")
    private String numContacto;

    @Column(name = "Correo")
    private String correo;
}
