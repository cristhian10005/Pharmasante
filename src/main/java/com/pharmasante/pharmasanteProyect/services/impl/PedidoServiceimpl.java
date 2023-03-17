package com.pharmasante.pharmasanteProyect.services.impl;

import com.pharmasante.pharmasanteProyect.EntitiesDto.DomicilioDto;
import com.pharmasante.pharmasanteProyect.EntitiesDto.UsuarioEntradaDto;
import com.pharmasante.pharmasanteProyect.Excepciones.ProductException;
import com.pharmasante.pharmasanteProyect.models.*;
import com.pharmasante.pharmasanteProyect.repository.*;
import com.pharmasante.pharmasanteProyect.services.IPedidosService;
import com.pharmasante.pharmasanteProyect.services.IProductoService;
import com.pharmasante.pharmasanteProyect.services.IUsuarioService;
import com.pharmasante.pharmasanteProyect.services.IValidaciones;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@Validated
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
    @Autowired
    IRecogenrEnTiendaRepository recogerEnTiendaRepository;
    @Autowired
    IDomicilioRepository domicilioRepository;
    @Autowired
    IValidaciones validaciones;


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
            int total = detalle.getPedido().getPrecioPedido();
            detalle.getPedido().setPrecioPedido(total + detalle.getProducto().getPrecioVenta());
            pedidoRepository.save(detalle.getPedido());
            detallePedidoRepository.save(detalle);
        }else if(tipo.equals("resta") && detalle.getCantidad()>1) {
            detalle.setCantidad(detalle.getCantidad() - 1);
            detalle.setSubtotal(detalle.getCantidad() * detalle.getProducto().getPrecioVenta());
            int total = detalle.getPedido().getPrecioPedido();
            detalle.getPedido().setPrecioPedido(total - detalle.getProducto().getPrecioVenta());
            pedidoRepository.save(detalle.getPedido());
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
        int precio = detalle.getPedido().getPrecioPedido();
        detalle.getPedido().setPrecioPedido(detalle.getPedido().getPrecioPedido()- detalle.getSubtotal());
        pedidoRepository.save(detalle.getPedido());
        detallePedidoRepository.delete(detalle);
    }
    
    
    
    @Override
    public void solicituTienda(int idPedido) {
    	Pedido pedido = pedidoRepository.findById(idPedido).orElseThrow(()->{
    		throw new  ProductException("No se encuentra ningún pedido asociado",
                    "Pedido no encontrado");
    		});
    	if(pedido.getDetalle().isEmpty()){
            throw new  ProductException("No se encuentra ningún prodcto agregado al carrito",
                    "Carrito sin productos");
        }
        EstadoPedido estado = estadoPedidoRepository.findById(2).get();
        pedido.setEstado(estado);
        RecogerEnTienda tienda = new RecogerEnTienda();
        tienda.setPedido(pedido);
        tienda.setFecha_limite(LocalDate.now().plusDays(1));
        recogerEnTiendaRepository.save(tienda);

    }


    @Override
    public void solicitudDomicilio(DomicilioDto domicilioP, Errors errors) {
        Pedido pedido = pedidoRepository.findById(domicilioP.getIdServicio()).orElseThrow(()->{
            throw new  ProductException("No se encuentra ningún pedido asociado",
                    "Pedido no encontrado");
        });
        if(pedido.getDetalle().isEmpty()){
            throw new  ProductException("No se encuentra ningún prodcto agregado al carrito",
                    "Carrito sin productos");
        }
        validaciones.validacionDeErrores(errors);
        EstadoPedido estado = estadoPedidoRepository.findById(2).get();
        pedido.setEstado(estado);
        Domicilio domicilio = new Domicilio(null, domicilioP.getDestinatario(),
                domicilioP.getContacto(), domicilioP.getDireccion(), LocalTime.now(),
                LocalTime.of(0,0,0),pedido);
        domicilioRepository.save(domicilio);
    }
}
