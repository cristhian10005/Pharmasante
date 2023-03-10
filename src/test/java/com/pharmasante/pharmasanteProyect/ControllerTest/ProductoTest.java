package com.pharmasante.pharmasanteProyect.ControllerTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pharmasante.pharmasanteProyect.EntitiesDto.ProductoDTO;
import com.pharmasante.pharmasanteProyect.controller.CatalogoController;
import com.pharmasante.pharmasanteProyect.models.Categoria;
import com.pharmasante.pharmasanteProyect.models.Producto;
import com.pharmasante.pharmasanteProyect.models.Proveedor;
import com.pharmasante.pharmasanteProyect.services.IProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.mockito.BDDMockito;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.validation.Errors;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest( excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
        value = CatalogoController.class))
public class ProductoTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IProductoService productoService;

    @Mock
    private Errors errors;

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
    void TestGuardarProducto() throws Exception {
        BDDMockito.given(productoService.guardarProducto(Mockito.any(ProductoDTO.class), Mockito.any(Errors.class)))
                .willReturn(producto);

        ResultActions response = mockMvc.perform(post("/inventario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productoDTO)));

        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre", is(producto.getNombre())));

    }

    @Test
    void TestGuardarProductoExcepcion() throws Exception {
        BDDMockito.given(productoService.guardarProducto(Mockito.any(ProductoDTO.class), Mockito.any(Errors.class)))
                .willReturn(producto);
        producto.setNombre(null);
        ResultActions response = mockMvc.perform(post("/inventario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productoDTO)));

        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre", is(producto.getNombre())));

    }

    @Test
    void TestListarProducto() throws Exception {
        List<Producto>lista = Arrays.asList(producto,
                new Producto(1,"Losartan",new Categoria(1,"Cosmeticos"),
                        new Proveedor(), "src/img", 5200,7700,
                        0,0,0));

        BDDMockito.given(productoService.listaProductos()).willReturn(lista);

        ResultActions response = mockMvc.perform(get("/inventario"));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(lista.size())));

    }
    @Test
    void TestEliminarProducto() throws Exception {
        BDDMockito.willDoNothing().given(productoService).eliminarProducto(1);

        ResultActions response = mockMvc.perform(delete("/inventario/{id}",1));

        response.andDo(print())
                .andExpect(status().isNoContent());
    }

}
