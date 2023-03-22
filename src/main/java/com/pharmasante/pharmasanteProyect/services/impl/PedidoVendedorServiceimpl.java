package com.pharmasante.pharmasanteProyect.services.impl;

import com.pharmasante.pharmasanteProyect.EntitiesDto.CalificacionDto;
import com.pharmasante.pharmasanteProyect.EntitiesDto.DomicilioDto;
import com.pharmasante.pharmasanteProyect.EntitiesDto.PedidosDto;
import com.pharmasante.pharmasanteProyect.Excepciones.ProductException;
import com.pharmasante.pharmasanteProyect.models.*;
import com.pharmasante.pharmasanteProyect.repository.*;
import com.pharmasante.pharmasanteProyect.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Validated
public class PedidoVendedorServiceimpl implements IPedidosVendedorService {
    @Autowired
    IPedidoRepository pedidoRepository;
    @Autowired
    IDetallePedidoRepository detallePedidoRepository;
    @Autowired
    IUsuarioService  usuarioService;
    @Autowired
    IEstadoPedidoRepository estadoPedidoRepository;
    @Autowired
    IProductoService productoService;
    @Autowired
    IRecogenrEnTiendaRepository recogerEnTiendaRepository;
    @Autowired
    IDomicilioRepository domicilioRepository;
    @Autowired
    IValidaciones validaciones;
    @Autowired
    ITipoPedidoRepository tipoPedidoRepository;
    @Autowired
    ICalificacionRepository calificacionRepository;
    @Autowired
    IProductoRepository productoRepository;

    @Override
    public List<Pedido> listaPedidos(){
        List<Pedido>pedidos = pedidoRepository
                .findByEstadoIsNotOrderByIdDesc(estadoPedidoRepository.findById(1).get());
        if(pedidos.isEmpty()){
            throw new ProductException("No se encuentran pedidos","");
        }

    return pedidos;

    }

    @Override
    public void eliminarPedido(int idPedido){
        Pedido pedido = pedidoRepository.findById(idPedido).orElseThrow(()->{
            throw new  ProductException("No se encuentra ningún pedido asociado",
                    "Pedido no encontrado");
        });
        if(pedido.getEstado().getId() == 2){
            pedidoRepository.deleteById(idPedido);
        }else if (pedido.getEstado().getId() == 3 || pedido.getEstado().getId() == 4){
            pedido.setEstado(estadoPedidoRepository.findById(6).get());
            pedidoRepository.save(pedido);
        }
    }

    public Set<Producto> listaCalificados(Usuario usuario){
        EstadoPedido estado = estadoPedidoRepository.findById(7).get();
        List<Pedido>pedidos =pedidoRepository.findByUsuarioAndEstado(usuario, estado);
        if (pedidos.isEmpty()){
            throw new ProductException("EL usuario no dispone de pedidos comprados",
                    "pedidos no encontrados");
        }
        HashSet<Producto>productos = new HashSet<>();
        for (Pedido pedido: pedidos){
           for (DetallePedido detalle: pedido.getDetalle()){
               productos.add(detalle.getProducto());
           }
        }
        return productos;
    }

    @Override
    public void calificar(CalificacionDto calificacion){
        Producto producto = productoService.buscarProducto(calificacion.getIdProducto());
        Usuario usuario = usuarioService.buscarUsuario(calificacion.getIdUsuario());
        List<Calificacion>calificacionList = calificacionRepository
                .findByProductoAndUsuario(producto,usuario);
        Calificacion calificacion1 =new Calificacion(null,producto,
                usuario,calificacion.getCalificacion());
        if (!calificacionList.isEmpty()){
            calificacion1.setCodCalificacion(calificacionList.get(0).getCodCalificacion());
        }
        calificacionRepository.save(calificacion1);
        List<Calificacion> calificaciones = calificacionRepository.findByProducto(producto);
        int notas =0;
        int conteo =0;
        for (Calificacion calificacion2 : calificaciones){
            notas += calificacion2.getValor();
            conteo++;
        }
        producto.setCalificacion(notas/conteo);
        productoRepository.save(producto);

    }
}
