package com.pharmasante.pharmasanteProyect.ServiceTest;

import com.pharmasante.pharmasanteProyect.models.*;
import com.pharmasante.pharmasanteProyect.repository.IEstadoPedidoRepository;
import com.pharmasante.pharmasanteProyect.repository.IPedidoRepository;
import com.pharmasante.pharmasanteProyect.repository.IRecogenrEnTiendaRepository;
import com.pharmasante.pharmasanteProyect.services.impl.PedidoServiceimpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {

    @Mock
    IPedidoRepository pedidoRepository;
    @Mock
    IEstadoPedidoRepository estadoPedidoRepository;

    @Mock
    IRecogenrEnTiendaRepository recogerEnTiendaRepository;
    @InjectMocks
    PedidoServiceimpl pedidoServiceimpl;

    @Test
    void testGuardarRecogerTienda(){
        List<EstadoPedido>estados= Arrays.asList(new EstadoPedido(1,"pre-solicitado"),
                new EstadoPedido(2,"solicitado"));
        Usuario  usuario = new Usuario(1,"Carlos123","Carlos","Paez","correo",
                "123","10222", null,"322222",null);

        Pedido pedido = new Pedido(1,500,estados.get(0),null,
                null,usuario, Arrays.asList(new DetallePedido()));

        BDDMockito.given(pedidoRepository.findById(1)).willReturn(Optional.of(pedido));
        BDDMockito.given(estadoPedidoRepository.findById(2)).willReturn(Optional.of(estados.get(1)));
        pedidoServiceimpl.solicituTienda(1);
        verify(recogerEnTiendaRepository, times(1))
                .save(BDDMockito.any(RecogerEnTienda.class));


    }
}
