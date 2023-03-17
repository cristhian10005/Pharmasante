package com.pharmasante.pharmasanteProyect.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    protected Integer id;

    @Column(name = "precio_pedido")
    protected Integer precioPedido;

    @ManyToOne
    @JoinColumn(name = "id_estado_pedido ")
    protected EstadoPedido estado;

    @ManyToOne
    @JoinColumn(name = "id_tipo_pedido ")
    protected TipoPedido tipoPedido;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_solicitud")
    protected LocalDate fechaSolicitud;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    protected Usuario usuario;

    @OneToMany(mappedBy ="pedido")
    protected List<DetallePedido>detalle;

}
