package com.pharmasante.pharmasanteProyect.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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

    @Column(name= "hora_envio")
    private LocalTime horaEnvio;

    @Column(name= "tiempo_estimado")
    private LocalTime tiempoEstimado;

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

}
