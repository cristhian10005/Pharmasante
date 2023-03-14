package com.pharmasante.pharmasanteProyect.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name = "id_registro")
    private Integer id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    @Column(name = "cantidad")
    private int cantidad;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto producto;

    @Column(name = "subtotal")
    private int subtotal;


}
