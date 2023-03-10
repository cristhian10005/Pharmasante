package com.pharmasante.pharmasanteProyect.EntitiesDto;


import com.pharmasante.pharmasanteProyect.models.Categoria;
import com.pharmasante.pharmasanteProyect.models.Producto;
import com.pharmasante.pharmasanteProyect.models.Proveedor;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
public class ProductoDTO {

  @NotNull(message = "El campo nombre no debe ir nulo")
  @NotBlank(message = "El campo nombre no puede ir en blanco")
  @Size(min = 3, max = 30, message = "El nombre debe medir entre 3 y 20")
  private String nombre;
  @NotNull(message = "Debe asignarse una categoria")
  private Categoria categoria;

  @NotNull(message = "Debe asignarse un proveedor")
  private Proveedor proveedor;
  private String nombreImg;

  @NotNull(message = "El campo precio compra no debe ir nulo")
  @Min(value = 100, message = "EL precio de compra debe ser mayor 100")
  @Max(value = 900000, message = "EL precio de compra debe ser menor 900.000")
  private Integer precioCompra;
  @NotNull(message = "El campo precio venta no debe ir nulo")
  @Min(value = 100, message = "EL precio de venta debe ser mayor 100")
  @Max(value = 900000, message = "EL precio de venta debe ser menor 900.000")
  private Integer precioVenta;

  private String bytesImg;

  public Producto productoDTOtoEntity(String rutaImg){
     return new Producto(null, this.nombre, this.categoria,this.proveedor, rutaImg,
             this.precioCompra, this.precioVenta,0,0,0);
  }

}
