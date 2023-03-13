package com.pharmasante.pharmasanteProyect.RepositoryTest;

import com.pharmasante.pharmasanteProyect.models.EstadoPedido;
import com.pharmasante.pharmasanteProyect.models.Pedido;
import com.pharmasante.pharmasanteProyect.models.Usuario;
import com.pharmasante.pharmasanteProyect.repository.IEstadoPedidoRepository;
import com.pharmasante.pharmasanteProyect.repository.IPedidoRepository;
import com.pharmasante.pharmasanteProyect.repository.IUsuarioRepository;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class PedidoRepositoryTest {
    @Autowired
    IUsuarioRepository usuarioRepository;
    @Autowired
    IEstadoPedidoRepository estadoPedidoRepository;
    @Autowired
    IPedidoRepository pedidoRepository;

    @Test
    void testBuscarPedido(){
        Usuario usuario = new Usuario(1,"Carlos123","Carlos","Paez","correo",
                "123","10222", null,"322222",null);
        Usuario usuario2 = new Usuario(2,"Pedro123","Pedro","Mora","correo",
                "123","25222", null,"355222",null);
        Usuario usuario3 = new Usuario(3,"Pedro123","Juan","Garcia","correo",
                "123","25222", null,"355222",null);
        EstadoPedido estadoPedido = new EstadoPedido(1,"pre-solicitado");
        EstadoPedido estadoPedido2 = new EstadoPedido(2,"solicitado");
        EstadoPedido estadoPedido3 = new EstadoPedido(2,"cancelado");
        Pedido pedido = new Pedido(1,500,estadoPedido,null,
                null,usuario,null);
        Pedido pedido2 = new Pedido(2,600,estadoPedido,null,
                null,usuario,null);
        Pedido pedido3 = new Pedido(3,1600,estadoPedido2,null,
                null,usuario2,null);


        usuarioRepository.save(usuario);
        usuarioRepository.save(usuario2);
        usuarioRepository.save(usuario3);
        estadoPedidoRepository.save(estadoPedido);
        estadoPedidoRepository.save(estadoPedido2);
        estadoPedidoRepository.save(estadoPedido3);
        pedidoRepository.save(pedido);
        pedidoRepository.save(pedido2);
        pedidoRepository.save(pedido3);

        Assertions.assertThat(pedidoRepository.findByUsuarioAndEstado(usuario, estadoPedido).size())
                .isEqualTo(2);
        Assertions.assertThat(pedidoRepository.findByUsuarioAndEstado(usuario2, estadoPedido2).size())
                .isEqualTo(1);
        Assertions.assertThat(pedidoRepository.findByUsuarioAndEstado(usuario3, estadoPedido3).isEmpty());
        Assertions.assertThat(pedidoRepository.findByUsuarioAndEstado(usuario3, estadoPedido).isEmpty());
        Assertions.assertThat(pedidoRepository.findByUsuarioAndEstado(usuario, estadoPedido3).isEmpty());


    }
}
