package com.pharmasante.pharmasanteProyect.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer id;

    @Column(name = "nombre_usuario")
    private String nombreUsuario;

    @Column(name = "Nombres")
    private String nombres;

    @Column(name = "Apellidos")
    private String apellidos;

    @Column(name = "Correo")
    private String correo;

    @Column(name = "password")
    private String password;

    @Column(name = "numero_identificacion")
    private String identificacion;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "numero_contacto")
    private String nContacto;

    @ManyToOne()
    @JoinColumn(name = "id_rol")
    private Rol rol;
}

