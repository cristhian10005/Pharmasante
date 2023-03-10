package com.pharmasante.pharmasanteProyect.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "detallepedido")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetallePedido {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    Pedido pedido;

    @Column(name = "cantidad")
    private int cantidad;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    Producto producto;

    @Column(name = "subtotal")
    private int subtotal;


}
