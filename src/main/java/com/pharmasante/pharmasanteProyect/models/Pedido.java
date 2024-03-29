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
    private Integer id;

    @Column(name = "precio_pedido")
    private Integer precioPedido;

    @ManyToOne
    @JoinColumn(name = "id_estado_pedido ")
    private EstadoPedido estado;

    @ManyToOne
    @JoinColumn(name = "id_tipo_pedido ")
    private TipoPedido tipoPedido;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_solicitud")
    private LocalDate fechaSolicitud;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @OneToMany(mappedBy ="pedido")
    private List<DetallePedido>detalle;

}
