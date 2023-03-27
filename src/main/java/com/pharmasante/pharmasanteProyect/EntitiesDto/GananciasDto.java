package com.pharmasante.pharmasanteProyect.EntitiesDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GananciasDto {
    private long ingresosBrutos;
    private long ganancias;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;
}
