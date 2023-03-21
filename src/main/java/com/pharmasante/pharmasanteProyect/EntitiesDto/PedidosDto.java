package com.pharmasante.pharmasanteProyect.EntitiesDto;

import com.pharmasante.pharmasanteProyect.models.Domicilio;
import com.pharmasante.pharmasanteProyect.models.Pedido;
import com.pharmasante.pharmasanteProyect.models.Producto;
import com.pharmasante.pharmasanteProyect.models.RecogerEnTienda;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class PedidosDto {
    List<RecogerEnTienda> recogerEnTiendas;
    List<Domicilio>domicilios;
    List<Pedido>pedidos;
    Set<Producto> productos;
    public PedidosDto(){
        recogerEnTiendas = new ArrayList<>();
        domicilios =new ArrayList<>();
        pedidos = new ArrayList<>();
    }

}
