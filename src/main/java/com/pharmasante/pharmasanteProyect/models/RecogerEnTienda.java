package com.pharmasante.pharmasanteProyect.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "recoger_tienda")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecogerEnTienda extends Pedido{
    @Column(name = "fecha_limite")
    private LocalDate fecha_limite;


}
