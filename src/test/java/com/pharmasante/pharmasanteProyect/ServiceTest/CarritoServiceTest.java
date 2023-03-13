package com.pharmasante.pharmasanteProyect.ServiceTest;

import com.pharmasante.pharmasanteProyect.models.*;
import com.pharmasante.pharmasanteProyect.repository.IDetallePedidoRepository;
import com.pharmasante.pharmasanteProyect.repository.IEstadoPedidoRepository;
import com.pharmasante.pharmasanteProyect.repository.IPedidoRepository;
import com.pharmasante.pharmasanteProyect.services.IProductoService;
import com.pharmasante.pharmasanteProyect.services.IUsuarioService;
import com.pharmasante.pharmasanteProyect.services.impl.PedidoServiceimpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CarritoServiceTest {
    @Mock
    IPedidoRepository pedidoRepository;
    @Mock
    IDetallePedidoRepository detallePedidoRepository;
    @Mock
    IUsuarioService usuarioService;
    @Mock
    IEstadoPedidoRepository estadoPedidoRepository;
    @Mock
    IProductoService productoService;
    @InjectMocks
    PedidoServiceimpl pedidoServiceimpl;

    Producto producto;
    Usuario usuario;
    EstadoPedido estadoPedido;
    Pedido pedido;

    @BeforeEach
    void inicializar(){
        producto = new Producto(1,"Verapamilo",null,null,
                "",500,800,0,0,0);
        usuario = new Usuario(1,"Carlos123","Carlos","Paez","correo",
                "123","10222", null,"322222",null);
        estadoPedido = new EstadoPedido(1,"pre-solicitado");

        pedido = new Pedido(1,500,estadoPedido,null,
                null,usuario,null);
    }

    @Test
    void pedidoCarritoTest(){
        BDDMockito.given(usuarioService.buscarUsuario(1))
                .willReturn(usuario);

        BDDMockito.given(estadoPedidoRepository.findById(1))
                .willReturn(Optional.of(estadoPedido));

        BDDMockito.given(pedidoRepository.findByUsuarioAndEstado(usuario,estadoPedido))
                .willReturn(List.of(pedido, new Pedido()));

        Assertions.assertThat(pedidoServiceimpl.pedidoCarrito(1)).isNotNull();

    }
    @Test
    void pedidoInicialTest(){

        Producto producto2 = new Producto(2,"Losartan",null,null,
                "",900,1800,0,0,0);
        Producto producto3 = new Producto(3,"Losartan",null,null,
                "",900,1800,0,0,0);

        List<DetallePedido>listDetalle = Arrays.asList(
                new DetallePedido(2,null,0,producto2,0),
                new DetallePedido(3,null,0,producto3,0)
        );
        pedido.setDetalle(listDetalle);

        BDDMockito.given(usuarioService.buscarUsuario(1))
                .willReturn(usuario);

        BDDMockito.given(estadoPedidoRepository.findById(1))
                .willReturn(Optional.of(estadoPedido));

        BDDMockito.given(pedidoRepository.findByUsuarioAndEstado(usuario,estadoPedido))
                .willReturn(List.of(pedido, new Pedido()));

        BDDMockito.given(productoService.buscarProducto(1))
                .willReturn(producto);
        BDDMockito.willDoNothing().given(detallePedidoRepository)
                .carritoCompras(1,1, LocalDate.now(),1);

        pedidoServiceimpl.pedidoInicial(1,1);
        verify(detallePedidoRepository, times(1))
                .carritoCompras(1,1, LocalDate.now(),1);
    }


}
