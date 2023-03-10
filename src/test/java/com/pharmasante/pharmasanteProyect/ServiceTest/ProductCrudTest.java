package com.pharmasante.pharmasanteProyect.ServiceTest;
import com.pharmasante.pharmasanteProyect.Excepciones.ProductException;
import com.pharmasante.pharmasanteProyect.models.Categoria;
import com.pharmasante.pharmasanteProyect.models.Producto;
import com.pharmasante.pharmasanteProyect.EntitiesDto.ProductoDTO;
import com.pharmasante.pharmasanteProyect.models.Proveedor;
import com.pharmasante.pharmasanteProyect.repository.IProductoRepository;
import com.pharmasante.pharmasanteProyect.services.IStorageService;
import com.pharmasante.pharmasanteProyect.services.impl.ProductoServicesimpl;
import org.assertj.core.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProductCrudTest {
    @Mock
    private IProductoRepository iproductoRepository;
    @Mock
    private IStorageService iStorageService;
    @Mock
    private Errors errors;
    @InjectMocks
    private ProductoServicesimpl productoServicesimpl;

    Producto producto;
    ProductoDTO productoDTO;
    @BeforeEach
    void inicializarValores(){
        producto = new Producto(1,"Loratadina",new Categoria(1,"Cosmeticos"),
                new Proveedor(), "src/img", 500,700,
                0,0,0);

        productoDTO = new ProductoDTO("Loratadina",new Categoria(1,"Cosmeticos"),
                new Proveedor(1,"La sante","555","correo1"),
                "imagen,png", 500, 700, "Bytes imagen");
    }

    @Test
    void testGuardarProducto(){
        BDDMockito.given(iproductoRepository.save(Mockito.any(Producto.class)))
                    .willAnswer(p-> p.getArgument(0));
        BDDMockito.given(iStorageService.store(Mockito.any(ProductoDTO.class)))
                .willReturn("ruta de imagen");
        BDDMockito.given(errors.getAllErrors()).willReturn(new ArrayList<>());


        Producto productoGuardado = productoServicesimpl.guardarProducto(productoDTO,errors);

        Assertions.assertThat(productoGuardado).isNotNull();
        Assertions.assertThat(productoGuardado.getNombre()).isEqualTo("Loratadina");
        Assertions.assertThat(productoGuardado.getImagen()).isEqualTo("ruta de imagen");

    }

    @Test
    void testActualizarProducto(){
        BDDMockito.given(iproductoRepository.save(Mockito.any(Producto.class)))
                .willAnswer(p-> p.getArgument(0));
        ProductoDTO productoDTO2 = new ProductoDTO("Loratadina",null, null,
                "imagen,png", 500, 700, "Bytes imagen");

        Producto productoActualizado = productoServicesimpl.actualizarProducto(productoDTO2, errors);

        Assertions.assertThat(productoActualizado.getNombre()).isEqualTo("Loratadina");
    }

    @Test
    void testListarProducto(){
        List<Producto>lista = Arrays.asList(producto,
                        new Producto(1,"Loratadina 2",null,null,
                                "src/img", 800,1000,
                                0,0,0)
        );
        BDDMockito.given(iproductoRepository.findAll())
                .willReturn(lista);

        List<Producto> lista2 = productoServicesimpl.listaProductos();

        Assertions.assertThat(lista2.size()).isEqualTo(2);
    }
    @Test
    void testEliminarProducto(){
        int id =1;
       BDDMockito.given(iproductoRepository.findById(id))
                        .willReturn(Optional.of(producto));
        BDDMockito.willDoNothing().given(iproductoRepository).delete(producto);
        productoServicesimpl.eliminarProducto(id);
        verify(iproductoRepository, times(1)).delete(producto);

    }

    @Test
    void TestEliminarProductoException(){
        int id =1;
        BDDMockito.given(iproductoRepository.findById(id))
                .willReturn(Optional.empty());

        assertThrows(ProductException.class,()->{
            productoServicesimpl.eliminarProducto(id);
        });
        verify(iproductoRepository, Mockito.never()).delete(Mockito.any(Producto.class));

    }

    @Test
    void testValidacionDeErrores(){
        BDDMockito.given(errors.getAllErrors()).willReturn(List.of(
                new ObjectError("ProductoDTO","Campo vacio")));

        assertThrows(ProductException.class,()->{
            productoServicesimpl.guardarProducto(productoDTO,errors);
        });
        verify(iproductoRepository, Mockito.never()).save(Mockito.any(Producto.class));

    }


}
