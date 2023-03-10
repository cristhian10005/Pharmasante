package com.pharmasante.pharmasanteProyect.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "estadopedidio")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstadoPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado_pedido")
    private Integer id;

    @Column(name = "descripsion_estado_pedido")
    private String descripcion;
}
