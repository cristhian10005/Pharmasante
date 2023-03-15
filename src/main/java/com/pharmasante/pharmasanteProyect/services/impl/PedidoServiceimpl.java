package com.pharmasante.pharmasanteProyect.services.impl;

import com.pharmasante.pharmasanteProyect.Excepciones.ProductException;
import com.pharmasante.pharmasanteProyect.models.*;
import com.pharmasante.pharmasanteProyect.repository.IDetallePedidoRepository;
import com.pharmasante.pharmasanteProyect.repository.IEstadoPedidoRepository;
import com.pharmasante.pharmasanteProyect.repository.IPedidoRepository;
import com.pharmasante.pharmasanteProyect.services.IPedidosService;
import com.pharmasante.pharmasanteProyect.services.IProductoService;
import com.pharmasante.pharmasanteProyect.services.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoServiceimpl implements IPedidosService {
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

    @Override
    public void pedidoInicial(int idProducto, int idUsuario) {
        LocalDate fecha = LocalDate.now();
        if (pedidoCarrito(idUsuario)!=null){
            List<DetallePedido> detalle = pedidoCarrito(idUsuario).getDetalle();
            Producto producto = productoService.buscarProducto(idProducto);
            for (DetallePedido d : detalle){
                if (producto.equals(d.getProducto())){
                    throw new ProductException("El producto ya ha sido agregado","producto duplicado");
                }
            }
        }
        detallePedidoRepository.carritoCompras(idProducto, idUsuario,fecha,1);
    }

    @Override
    public Pedido listaCarrito(int idUsuario) {
         if(pedidoCarrito(idUsuario)!=null){
             return pedidoCarrito(idUsuario);
         }
         throw new ProductException("No dispone de productos en su carrito","Lista no encontrada");
    }

    public Pedido pedidoCarrito(int idUsuario){
        Usuario usuario= usuarioService.buscarUsuario(idUsuario);
        EstadoPedido estadoPedido = estadoPedidoRepository.findById(1).get();
        Optional<Pedido> pedido = pedidoRepository.findByUsuarioAndEstado(usuario,estadoPedido)
                .stream().findFirst();
        return pedido.orElse(null);

    }

    @Override
    public void adicionarUnidades(int idDetalle, String tipo){
        DetallePedido detalle = detallePedidoRepository.findById(idDetalle)
                .orElseThrow(()->{
                    throw new  ProductException("Este producto no esta disponible en tu carrito",
                                    "Detalle de pedido no encontrado");
                });
        if (tipo.equals("suma") && detalle.getCantidad() <10 ){
            detalle.setCantidad(detalle.getCantidad() + 1);
            detalle.setSubtotal(detalle.getCantidad() * detalle.getProducto().getPrecioVenta());
            detallePedidoRepository.save(detalle);
        }else if(tipo.equals("resta") && detalle.getCantidad()>1) {
            detalle.setCantidad(detalle.getCantidad() - 1);
            detalle.setSubtotal(detalle.getCantidad() * detalle.getProducto().getPrecioVenta());
            detallePedidoRepository.save(detalle);
        }else {
            throw new ProductException("La cantidad solicitada debe ser mayor a 1 y menor a 10",
                    "Fuera de rango");
        }
    }

    @Override
    public void eliminarProductoCarrito(int idDetalle){
        DetallePedido detalle = detallePedidoRepository.findById(idDetalle)
                .orElseThrow(()->{
                    throw new  ProductException("Este producto no esta disponible en tu carrito",
                            "Detalle de pedido no encontrado");
                });
        detallePedidoRepository.delete(detalle);
    }

}
