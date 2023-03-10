package com.pharmasante.pharmasanteProyect.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalTime;

@Entity
@Table(name = "domicilios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Domicilio extends Pedido{
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

}
