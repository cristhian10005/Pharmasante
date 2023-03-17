package com.pharmasante.pharmasanteProyect.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "recoger_tienda")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecogerEnTienda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recoger_tienda")
    private Integer id;
    @OneToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;
    @Column(name = "fecha_limite")
    private LocalDate fecha_limite;
}
