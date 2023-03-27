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
    IUsuarioService  usuarioService;
    @Autowired
    IEstadoPedidoRepository estadoPedidoRepository;
    @Autowired
    IRecogenrEnTiendaRepository recogerEnTiendaRepository;
    @Autowired
    IDomicilioRepository domicilioRepository;

    @Autowired
    IClienteRepository clienteRepository;
    @Autowired
    IVentasRepository ventasRepository;
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
    public void AsignarEstado(int idPedido, int estado, LocalTime hora, int idUsuario){
        Pedido pedido = pedidoRepository.findById(idPedido).orElseThrow(()->{
            throw new ProductException("Pedido no encontrado","");
        });

        if(pedido.getEstado().getId() == 2 && pedido.getTipoPedido().getId() == 2 && estado ==4){
            Domicilio domicilio = domicilioRepository.findByPedido(pedido).stream().findFirst()
                    .orElseThrow(()->{
                        throw new ProductException("No hay domicilios asociados a este pedodo","");
                    });
            LocalTime horaActual = LocalTime.now();
            if(!horaActual.isBefore(hora)){
                throw new ProductException("Seleccione una hora posterior a la actual","");
            }
            domicilio.setHoraLlegada(hora);
            domicilio.setFehcaLlegada(LocalDate.now());
            domicilioRepository.save(domicilio);


        }else if(pedido.getEstado().getId() == 2 && pedido.getTipoPedido().getId() == 1 && estado ==3){
            RecogerEnTienda recoger = recogerEnTiendaRepository.findByPedido(pedido).stream().findFirst()
                    .orElseThrow(()->{
                        throw new ProductException("No productos para recoger en tienda en este pedido","");
                    });
            recoger.setFecha_limite(LocalDate.now().plusDays(1));
            recogerEnTiendaRepository.save(recoger);
        }
        pedido.setEstado(estadoPedidoRepository.findById(estado).get());
        pedidoRepository.save(pedido);

        if(estado == 7){
            registrarVenta(pedido, idUsuario);
        }
        if (estado ==6){
            Usuario usuario = usuarioService.buscarUsuario(pedido.getUsuario().getId());
            Cliente cliente = clienteRepository.findByUsuario(usuario).get(0);
            cliente.setReportes(cliente.getReportes()+1);
            clienteRepository.save(cliente);
            pedido.setEstado(estadoPedidoRepository.findById(6).get());
            pedidoRepository.save(pedido);
        }

    }

    public void registrarVenta(Pedido pedido, int idUsuario){
        Usuario usuario = usuarioService.buscarUsuario(idUsuario);
        int ganancias =0;
        int gananciaTotal =0;
        for (DetallePedido detalle: pedido.getDetalle()){
           ganancias = detalle.getProducto().getPrecioVenta() - detalle.getProducto().getPrecioCompra();
           gananciaTotal += ganancias * detalle.getCantidad();
           detalle.getProducto().setUndVendidas(detalle.getProducto().getUndVendidas()+detalle.getCantidad());
           productoRepository.save(detalle.getProducto());
        }
        Ventas ventas = new Ventas(null,pedido,usuario,LocalDate.now(),gananciaTotal);
        ventasRepository.save(ventas);

    }
}
