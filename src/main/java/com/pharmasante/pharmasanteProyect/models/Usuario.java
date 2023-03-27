package com.pharmasante.pharmasanteProyect.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.time.LocalDate;

@Entity
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
    @NotNull(message = "El campo nombre usuario no debe ir nulo")
    @NotBlank(message = "El campo nombre usuario no puede ir en blanco")
    @Size(min = 5, max = 30, message = "El nombre debe contener entre 5 y 20 caracteres")
    private String nombreUsuario;

    @Column(name = "Nombres")
    @NotNull(message = "El campo nombre no debe ir nulo")
    @NotBlank(message = "El campo nombre no puede ir en blanco")
    @Size(min = 3, max = 20, message = "El nombre debe contener entre 3 y 20 caracteres")
    private String nombres;

    @Column(name = "Apellidos")
    @NotNull(message = "El campo apellido no debe ir nulo")
    @NotBlank(message = "El campo apellido no puede ir en blanco")
    @Size(min = 3, max = 30, message = "El apellido debe contener entre 3 y 20 caracteres")
    private String apellidos;

    @Column(name = "Correo")
    @NotNull(message = "El campo correo no debe ir nulo")
    @NotBlank(message = "El campo correo no puede ir en blanco")
    @Size(min = 6, max = 30, message = "El correo debe contener entre 6 y 20 caracteres")
    @Email(message = "El correo no es invalido")
    private String correo;

    @Column(name = "password")
    @NotNull(message = "El campo contraseña no debe ir nulo")
    @NotBlank(message = "El campo contraseña no puede ir en blanco")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,16}$",
    message = "La contraseña debe contener al menos 8 caracteres, una minúscula una mayúscula y un número")
    private String password;

    @Column(name = "numero_identificacion")
    @NotNull(message = "El campo identificación no debe ir nulo")
    @NotBlank(message = "El campo identificación no puede ir en blanco")
    @Size(min = 7, max = 12, message = "El número identificación debe contener al menos 7 números")
    @Pattern(regexp = "^^[0-9]+$", message = "El número de identificación solo puede contener números")
    private String identificacion;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_nacimiento")
    @NotNull(message = "El campo fecha no debe ir nulo")
    @Past(message = "La fecha debe ser anterior")
    private LocalDate fechaNacimiento;

    @Column(name = "numero_contacto")
    @NotNull(message = "El campo contacto no debe ir nulo")
    @NotBlank(message = "El campo contacto no puede ir en blanco")
    @Size(min = 10, max = 10, message = "El contacto debe contener 10 caracteres")
    @Pattern(regexp = "^^[0-9]+$", message = "El número de contacto solo puede contener números")
    private String nContacto;

    @ManyToOne
    @JoinColumn(name = "id_rol")
    private Rol rol;

    @Column(name = "estado")
    private int estado;
}

