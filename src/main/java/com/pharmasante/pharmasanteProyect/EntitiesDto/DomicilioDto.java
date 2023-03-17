package com.pharmasante.pharmasanteProyect.EntitiesDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DomicilioDto {
    private int idCliente;
    private int idServicio;

    @NotNull(message = "El campo nombre no debe ir nulo")
    @NotBlank(message = "El campo nombre no puede ir en blanco")
    @Size(min = 3, max = 30, message = "El nombre debe contener entre 7 y 30 letras, con un espacio que separe nombre y apellido")
    @Pattern(regexp = "^[a-zA-Z]+\\s[a-zA-Z]+$", message = "El nombre solo puede contener letras y vocales")
    private String destinatario;

    @NotNull(message = "El campo contacto no debe ir nulo")
    @NotBlank(message = "El campo contacto no puede ir en blanco")
    @Size(min = 10, max = 10, message = "El contacto debe contener 10 caracteres")
    @Pattern(regexp = "^^[0-9]+$", message = "El número de contacto solo puede contener números")
    private String contacto;

    @NotNull(message = "El campo dirección no debe ir nulo")
    @NotBlank(message = "El campo dirección no puede ir en blanco")
    @Size(min = 7, max = 30, message = "El campo direccion debe contener entre 3 y 30 letras")
    private String direccion;
}
