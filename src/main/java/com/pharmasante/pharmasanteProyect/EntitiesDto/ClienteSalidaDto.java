package com.pharmasante.pharmasanteProyect.EntitiesDto;

import com.pharmasante.pharmasanteProyect.EntitiesDto.IDto.IAccionRol;
import com.pharmasante.pharmasanteProyect.models.Domicilio;
import com.pharmasante.pharmasanteProyect.models.Pedido;
import com.pharmasante.pharmasanteProyect.models.Producto;
import com.pharmasante.pharmasanteProyect.models.RecogerEnTienda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Service(value = "cliente")
public class ClienteSalidaDto implements IAccionRol<Integer, String , ClienteSalidaDto> {
    private int idCliente;
    private String nombre;
    private int idService;
    private List<Producto> listaProducto;
    private Producto producto;
    private Pedido pedido;
    private Domicilio domicilio;
    private RecogerEnTienda tienda;
    private CatalogoIndex catalogoIndex;

    public ClienteSalidaDto(int id){
        idCliente = id;
    }

    @Override
    public ClienteSalidaDto retornoSencillo(Integer id) {
        return new ClienteSalidaDto(id);
    }

    @Override
    public ClienteSalidaDto retornoParametroId(Integer id, Integer id2) {
        return null;
    }

    @Override
    public ClienteSalidaDto retornoParametroBusqueda(Integer id, String valor) {
        return null;
    }
}
