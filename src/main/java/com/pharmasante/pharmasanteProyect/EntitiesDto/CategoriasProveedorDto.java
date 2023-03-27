package com.pharmasante.pharmasanteProyect.EntitiesDto;

import com.pharmasante.pharmasanteProyect.models.Categoria;
import com.pharmasante.pharmasanteProyect.models.Proveedor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoriasProveedorDto {
    private List<Categoria>categorias;
    private List<Proveedor>proveedores;
}
