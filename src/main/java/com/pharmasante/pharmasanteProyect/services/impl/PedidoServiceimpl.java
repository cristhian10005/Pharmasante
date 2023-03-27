package com.pharmasante.pharmasanteProyect.services.impl;

import com.pharmasante.pharmasanteProyect.EntitiesDto.CalificacionDto;
import com.pharmasante.pharmasanteProyect.EntitiesDto.DomicilioDto;
import com.pharmasante.pharmasanteProyect.EntitiesDto.PedidosDto;
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
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    @Autowired
    ITipoPedidoRepository tipoPedidoRepository;
    @Autowired
    ICalificacionRepository calificacionRepository;
    @Autowired
    IProductoRepository productoRepository;
    @Autowired
    IClienteRepository clienteRepository;


    @Override
    public void pedidoInicial(int idProducto, int idUsuario, int idEstado) {
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
        detallePedidoRepository.carritoCompras(idProducto, idUsuario,fecha,idEstado);
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
        pedido.setFechaSolicitud(LocalDate.now());
        RecogerEnTienda tienda = new RecogerEnTienda();
        tienda.setPedido(pedido);
        tienda.setFecha_limite(LocalDate.now().plusDays(1));
        recogerEnTiendaRepository.save(tienda);

    }


    @Override
    public void solicitudDomicilio(DomicilioDto domicilioP, Errors errors) {
        Pedido pedido = pedidoRepository.findById(domicilioP.getIdServicio()).orElseThrow(() -> {
            throw new ProductException("No se encuentra ningún pedido asociado",
                    "Pedido no encontrado");
        });
        if (pedido.getDetalle().isEmpty()) {
            throw new ProductException("No se encuentra ningún prodcto agregado al carrito",
                    "Carrito sin productos");
        }
        validaciones.validacionDeErrores(errors);
        pedido.setEstado(estadoPedidoRepository.findById(2).get());
        pedido.setTipoPedido(tipoPedidoRepository.findById(2).get());
        pedido.setFechaSolicitud(LocalDate.now());
        pedido.setPrecioPedido(pedido.getPrecioPedido()+ 6000);
        Domicilio domicilio = new Domicilio(null, domicilioP.getDestinatario(),
                domicilioP.getContacto(), domicilioP.getDireccion(), LocalDate.now(),
                LocalTime.of(0, 0, 0), pedido);
        domicilioRepository.save(domicilio);
    }
    @Override
    public PedidosDto listaPedidos(int idUsuario){
        Usuario usuario = usuarioService.buscarUsuario(idUsuario);
        List<Pedido>pedidos = pedidoRepository.findByUsuarioAndEstadoIsNotOrderByIdDesc(usuario,
                estadoPedidoRepository.findById(1).get());
        PedidosDto pedidosDto = new PedidosDto();
        for (Pedido pedido : pedidos){
            if(pedido.getTipoPedido().getId() ==1 &&
                    !recogerEnTiendaRepository.findByPedido(pedido).isEmpty()){
                pedidosDto.getRecogerEnTiendas().add(recogerEnTiendaRepository.findByPedido(pedido).get(0));
            } else if (pedido.getTipoPedido().getId() ==2 &&
                    !domicilioRepository.findByPedido(pedido).isEmpty()) {
                pedidosDto.getDomicilios().add(domicilioRepository.findByPedido(pedido).get(0));
            }else {
                pedidosDto.getPedidos().add(pedido);
            }
        }
        if (usuario.getRol().getId() ==1){
            pedidosDto.setProductos(listaCalificados(usuario));
        }
    return pedidosDto;

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
            Cliente cliente = clienteRepository.findByUsuario(pedido.getUsuario()).get(0);
            cliente.setReportes(cliente.getReportes()+1);
            clienteRepository.save(cliente);

        }
    }

    public Set<Producto> listaCalificados(Usuario usuario){
        EstadoPedido estado = estadoPedidoRepository.findById(7).get();
        List<Pedido>pedidos =pedidoRepository.findByUsuarioAndEstado(usuario, estado);
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
