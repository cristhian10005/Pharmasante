package com.pharmasante.pharmasanteProyect.RepositoryTest;
import com.pharmasante.pharmasanteProyect.models.Categoria;
import com.pharmasante.pharmasanteProyect.models.Producto;
import com.pharmasante.pharmasanteProyect.models.Proveedor;
import com.pharmasante.pharmasanteProyect.repository.ICategoriaRepository;
import com.pharmasante.pharmasanteProyect.repository.IProductoRepository;
import com.pharmasante.pharmasanteProyect.repository.IProveedorRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class ProductoCatalogoRepositoryTest {
    @Autowired
    IProductoRepository iproductoRepository;
    @Autowired
    ICategoriaRepository icategoriaRepository;
    @Autowired
    IProveedorRepository iproveedorRepository;
    Producto producto;
    Categoria categoria;
    Proveedor proveedor;
    @BeforeEach
    void inicializarValores(){
        categoria =new Categoria(null,"Medicamentos");
        proveedor =new Proveedor(null,"La sante",
                                "232323","sante@gmil.com");
        categoria = icategoriaRepository.save(categoria);
        proveedor = iproveedorRepository.save(proveedor);
        producto = new Producto(1,"Loratadina",categoria,proveedor,
                "src/img", 500,700,
                0,0,0);
    }

    @Test
    void testGuardarProducto(){
        Producto productoGuardado = iproductoRepository.save(producto);
        int idPto = productoGuardado.getIdProducto();
        Assertions.assertThat(productoGuardado).isNotNull();
        Assertions.assertThat(productoGuardado.getIdProducto()).isGreaterThan(0);
        Assertions.assertThat(iproductoRepository.findById(idPto)).isNotEmpty();
    }
    @Test
    void testListarProducto(){
        Producto p2 = new Producto(null,"Ibuprofeno",categoria,proveedor,
                "src/img", 800,1000,
                0,0,0);
        iproductoRepository.save(producto);
        iproductoRepository.save(p2);

        List<Producto> listaProductos = iproductoRepository.findAll();

        Assertions.assertThat(listaProductos.size()).isEqualTo(2);
    }

    @Test
    void testActualizarProducto(){
        int idPto = iproductoRepository.save(producto).getIdProducto();

        Producto p2 = new Producto(idPto,"Verapamilo",categoria,proveedor,
                "src/img", 200,400,
                0,0,0);
        iproductoRepository.save(p2);

        Assertions.assertThat(iproductoRepository.findAll().size()).isEqualTo(1);
        Assertions.assertThat(iproductoRepository.findById(idPto).get().getNombre())
                .isEqualTo("Verapamilo");
    }

    @Test
    void testEliminarPrducto(){
        iproductoRepository.save(producto);
        iproductoRepository.deleteById(1);
        Assertions.assertThat(iproductoRepository.findAll()).isEmpty();
    }

    @Test
    void testConsultaCatalogoVendidos(){
        Producto p2 = new Producto(null,"Ibuprofeno",categoria,proveedor,
                "src/img", 800,1000, 0,1,0);
        Producto p3 = new Producto(null,"Ibuprofeno2",categoria,proveedor,
                "src/img", 800,1000, 0,2,0);
        Producto p4 = new Producto(null,"Ibuprofen3",categoria,proveedor,
                "src/img", 800,1000, 0,3,0);
        Producto p5 = new Producto(null,"Ibuprofen4",categoria,proveedor,
                "src/img", 800,1000, 0,4,0);
        Producto p6 = new Producto(null,"Ibuprofen5",categoria,proveedor,
                "src/img", 800,1000, 0,5,0);
        Producto p7 = new Producto(null,"Ibuprofeno6",categoria,proveedor,
                "src/img", 800,1000, 0,6,0);

        iproductoRepository.save(producto);
        iproductoRepository.save(p2);
        iproductoRepository.save(p3);
        iproductoRepository.save(p4);
        iproductoRepository.save(p5);
        iproductoRepository.save(p6);
        iproductoRepository.save(p7);
        List<Producto> listaProductos = iproductoRepository.catalogoVendidos();

        Assertions.assertThat(listaProductos.size()).isEqualTo(5);
        Assertions.assertThat(listaProductos.get(0).getUndVendidas()).isEqualTo(6);
        Assertions.assertThat(listaProductos.get(4).getUndVendidas()).isEqualTo(2);

    }

    @Test
    void testConsultaCatalogoCalificados(){
        Producto p2 = new Producto(null,"Ibuprofeno",categoria,proveedor,
                "src/img", 800,1000, 1,1,0);
        Producto p3 = new Producto(null,"Ibuprofeno2",categoria,proveedor,
                "src/img", 800,1000, 2,2,0);
        Producto p4 = new Producto(null,"Ibuprofen3",categoria,proveedor,
                "src/img", 800,1000, 3,3,0);
        Producto p5 = new Producto(null,"Ibuprofen4",categoria,proveedor,
                "src/img", 800,1000, 4,4,0);
        Producto p6 = new Producto(null,"Ibuprofen5",categoria,proveedor,
                "src/img", 800,1000, 4,5,0);
        Producto p7 = new Producto(null,"Ibuprofeno6",categoria,proveedor,
                "src/img", 800,1000, 5,6,0);

        iproductoRepository.save(producto);
        iproductoRepository.save(p2);
        iproductoRepository.save(p3);
        iproductoRepository.save(p4);
        iproductoRepository.save(p5);
        iproductoRepository.save(p6);
        iproductoRepository.save(p7);
        List<Producto> listaProductos = iproductoRepository.catalogoVendidos();

        Assertions.assertThat(listaProductos.size()).isEqualTo(5);
        Assertions.assertThat(listaProductos.get(0).getCalificacion()).isEqualTo(5);
        Assertions.assertThat(listaProductos.get(4).getCalificacion()).isEqualTo(2);

    }

    @Test
    void testFiltrarCatalogoCategoria(){
        Producto p2 = new Producto(null,"Ibuprofeno",categoria,proveedor,
                "src/img", 800,1000,
                0,0,0);

        Categoria categoria1 = new Categoria(null, "cuidado presonal");
        Producto p3 = new Producto(null,"Talcos",categoria1,proveedor,
                "src/img", 800,1000,
                0,0,0);
        icategoriaRepository.save(categoria1);
        iproductoRepository.save(producto);
        iproductoRepository.save(p2);
        iproductoRepository.save(p3);

        Assertions.assertThat(iproductoRepository.findByCategoria(categoria1).size()).isEqualTo(1);
        Assertions.assertThat(iproductoRepository.findByCategoria(categoria1).get(0).getNombre())
                .isEqualTo("Talcos");
        Assertions.assertThat(iproductoRepository.findByCategoria(categoria).size()).isEqualTo(2);

    }

    @Test
    void testFiltrarCatalogoNombre(){
        Producto p2 = new Producto(null,"talcos2",categoria,proveedor,
                "src/img", 800,1000,
                0,0,0);

        Proveedor proveedor1 = new Proveedor(null,"Genfar",
                "232323","sante@gmil.com");
        Producto p3 = new Producto(null,"Talcos",categoria,proveedor1,
                "src/img", 800,1000,
                0,0,0);
        iproveedorRepository.save(proveedor1);
        iproductoRepository.save(producto);
        iproductoRepository.save(p2);
        iproductoRepository.save(p3);

        Assertions.assertThat(iproductoRepository.busqueda("proveedo flaso").size()).isEqualTo(0);
        Assertions.assertThat(iproductoRepository.busqueda("genfar").size()).isEqualTo(1);
        Assertions.assertThat(iproductoRepository.busqueda("La sante").size()).isEqualTo(2);
        Assertions.assertThat(iproductoRepository.busqueda("talcos").size()).isEqualTo(2);
        Assertions.assertThat(iproductoRepository.busqueda("med falso").size()).isEqualTo(0);



    }



}
