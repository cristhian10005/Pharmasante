package com.pharmasante.pharmasanteProyect.ControllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pharmasante.pharmasanteProyect.EntitiesDto.CatalogoIndex;
import com.pharmasante.pharmasanteProyect.EntitiesDto.ProductoDTO;
import com.pharmasante.pharmasanteProyect.controller.CatalogoController;
import com.pharmasante.pharmasanteProyect.controller.ProductController;
import com.pharmasante.pharmasanteProyect.models.Categoria;
import com.pharmasante.pharmasanteProyect.models.Producto;
import com.pharmasante.pharmasanteProyect.models.Proveedor;
import com.pharmasante.pharmasanteProyect.services.ICatalogoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
        value = ProductController.class))
public class CatalogoTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    ICatalogoService catalogoService;

    Producto producto;
    ProductoDTO productoDTO;

    @BeforeEach
    void inicializarValores(){
        producto = new Producto(1,"Loratadina",new Categoria(1,"Cosmeticos"),
                new Proveedor(), "src/img", 500,700,
                0,0,0);

        productoDTO = new ProductoDTO(null,"Loratadina",1, 1,
                "imagen,png", 500, 700, "Bytes imagen");
    }

    @Test
    void TestListarProducto() throws Exception {
        List<Producto> lista = Arrays.asList(producto,
                new Producto(1,"Losartan",new Categoria(1,"Cosmeticos"),
                        new Proveedor(), "src/img", 5200,7700,
                        0,0,0));
        CatalogoIndex catalogoIndex = new CatalogoIndex();
        catalogoIndex.setMejorValorados(lista);
        catalogoIndex.setMasVendidos(lista);
        BDDMockito.given(catalogoService.listaPrincipal()).willReturn(catalogoIndex);

        ResultActions response = mockMvc.perform(get("/catalogo"));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(lista.size())));

    }

}
