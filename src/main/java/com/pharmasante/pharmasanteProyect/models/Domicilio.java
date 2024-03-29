package com.pharmasante.pharmasanteProyect.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "domicilios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Domicilio{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_domicilio")
    private Integer id;

    @Column(name = "nombre_destinatario")
    private String nombreDestinatario;

    @Column(name = "numero_contacto")
    private String numContacto;

    @Column(name = "direccion")
    private String direccion;

    @Column(name= "fecha_llegada")
    private LocalDate fehcaLlegada;

    @Column(name= "hora_llegada")
    private LocalTime horaLlegada;

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

}
