package com.pharmasante.pharmasanteProyect.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tipopedido")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_pedido")
    private Integer id;

    @Column(name = "descripsion_tipo_pedido")
    private String descripcion;
}
