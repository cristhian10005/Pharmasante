package com.pharmasante.pharmasanteProyect.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "cliente")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente extends Usuario{
    @Column(name = "reportes")
    private int reportes;
}
