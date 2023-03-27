package com.pharmasante.pharmasanteProyect.EntitiesDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AsignarDto {
    private int idPedido;
    private int estado;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime hora;
    private int idUsuario;
}
