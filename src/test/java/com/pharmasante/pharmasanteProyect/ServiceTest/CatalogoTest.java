package com.pharmasante.pharmasanteProyect.ServiceTest;
import com.pharmasante.pharmasanteProyect.Excepciones.ProductException;
import com.pharmasante.pharmasanteProyect.models.Categoria;
import com.pharmasante.pharmasanteProyect.models.Producto;
import com.pharmasante.pharmasanteProyect.models.Proveedor;
import com.pharmasante.pharmasanteProyect.repository.ICategoriaRepository;
import com.pharmasante.pharmasanteProyect.repository.IProductoRepository;
import com.pharmasante.pharmasanteProyect.services.impl.CatalogoServiceimpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CatalogoTest {
    @Mock
    private IProductoRepository productoRepository;
    @Mock
    private ICategoriaRepository categoriaRepository;
    @InjectMocks
    private CatalogoServiceimpl catalogoServiceimpl;

    @Test
    void testListarProductoPrincipal(){
        List<Producto> listaInicial = Arrays.asList(
                new Producto(null,"Loratadina 2",null,null,
                        "src/img", 800,1000,
                        0,0,0),
                new Producto(null,"verapamilo",null,null,
                        "src/img", 1800,1900,
                        0,2,2),
                new Producto(null,"verapamilo2",null,null,
                        "src/img", 1800,1900,
                        0,4,0)
        );
        List<Producto>lista = new ArrayList<>(listaInicial);
        List<Producto> lista2 = Arrays.asList(new Producto(null,"ibuprofeno 2",null,null,
                        "src/img", 10800,21000,
                        0,0,0),
                new Producto(null,"losartan",null,null,
                        "src/img", 18000,19000,
                        2,0,0)
        );


        BDDMockito.given(productoRepository.catalogoVendidos())
                .willReturn(lista);
        BDDMockito.given(productoRepository.catalogoCalificados()).
                willReturn(lista2);
        Assertions.assertThat(catalogoServiceimpl.listaPrincipal().getMasVendidos().size()).isEqualTo(3);
        Assertions.assertThat(catalogoServiceimpl.listaPrincipal().getMejorValorados().size()).isEqualTo(2);
    }

    @Test
    void testFiltroPorCategoria(){
        Categoria categoria1 = new Categoria(1,"medicamentos");
        Categoria categoria2 = new Categoria(2,"cuidado personal");
        List<Categoria>listaCategorias = Arrays.asList(categoria1,categoria2);

        List<Producto> listaInicial = Arrays.asList(
                new Producto(null,"Loratadina", categoria1, null, "src/img",
                        800,1000, 0,0,0),
                new Producto(null,"verapamilo",categoria1,null, "src/img",
                        1800,1900, 0,2,2),
                new Producto(null,"Talcos",categoria2,null, "src/img",
                        1800,1900, 0,4,0)
        );

        BDDMockito.given(productoRepository.findByCategoria(BDDMockito.any(Categoria.class))).willAnswer((p)->{
            Categoria categoriaTemp = (Categoria) p.getArgument(0);
            int id = categoriaTemp.getCodCategoria();
             return  listaInicial.stream()
                    .filter((pr)->pr.getCategoria().getCodCategoria() == id)
                    .collect(Collectors.toList());
                }
        );

        BDDMockito.given(categoriaRepository.findById(BDDMockito.any(Integer.class))).willAnswer((c)->{
            int id = (Integer) c.getArgument(0);
            return listaCategorias.stream().filter(ca->ca.getCodCategoria() == id)
                    .findFirst();
        });

        Assertions.assertThat(catalogoServiceimpl.filtroPorCategoriaId(1).size())
                .isEqualTo(2);
    }

    @Test
    void testFiltroPorCategoriaExcepcion(){
        List<Categoria> listaCategorias = new ArrayList<>();
        BDDMockito.given(categoriaRepository.findById(BDDMockito.any(Integer.class))).willAnswer((c)->{
            int id = (Integer) c.getArgument(0);
            return listaCategorias.stream().filter(ca->ca.getCodCategoria() == id)
                    .findFirst();
        });
        assertThrows(ProductException.class,()->{
           catalogoServiceimpl.filtroPorCategoriaId(1);
        });
        verify(productoRepository, Mockito.never()).findByCategoria(Mockito.any(Categoria.class));
    }

    @Test
    void busacarPorNombre(){

        Proveedor proveedor1 = new Proveedor(null,"La sante",
                "232323","sante@gmil.com");

        Proveedor proveedor2 = new Proveedor(null,"Genfar",
                "232323","sante@gmil.com");

        List<Producto> listaInicial = Arrays.asList(
                new Producto(null,"Loratadina", null, proveedor1, "src/img",
                        800,1000, 0,0,0),
                new Producto(null,"verapamilo",null,proveedor1, "src/img",
                        1800,1900, 0,2,2),
                new Producto(null,"Talcos",null,proveedor2, "src/img",
                        1800,1900, 0,4,0)
        );

        BDDMockito.given(productoRepository.busqueda(BDDMockito.any(String.class))).willAnswer((e)->{
            String nombre = (String) e.getArgument(0);
            return listaInicial.stream().filter(p->p.getProveedor().getNombre()==nombre).collect(Collectors.toList());
        });

        Assertions.assertThat(catalogoServiceimpl.buscarProducto("La sante").size()).isEqualTo(2);
        Assertions.assertThat(catalogoServiceimpl.buscarProducto("Genfar").size()).isEqualTo(1);
    }
}
