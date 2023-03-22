package com.pharmasante.pharmasanteProyect.EntitiesDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PasswordDto {
    @NotNull(message = "El campo contraseña no debe ir nulo")
    @NotBlank(message = "El campo contraseña no puede ir en blanco")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,16}$",
            message = "La contraseña debe contener al menos 8 caracteres, una minúscula una mayúscula y un número")
    private String password;
    private String passwordOld;
    private int idUsuario;
}
