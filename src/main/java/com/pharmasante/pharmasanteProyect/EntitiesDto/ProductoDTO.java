package com.pharmasante.pharmasanteProyect.EntitiesDto;

import com.pharmasante.pharmasanteProyect.models.Producto;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
public class ProductoDTO {

  private Integer id;
  @NotNull(message = "El campo nombre no debe ir nulo")
  @NotBlank(message = "El campo nombre no puede ir en blanco")
  @Size(min = 3, max = 30, message = "El nombre debe contener entre 3 y 20 letras")
  private String nombre;
  @NotNull(message = "Debe asignarse una categoria")
  private int categoria;

  @NotNull(message = "Debe asignarse un proveedor")
  private int proveedor;
  @NotNull(message = "Debe asignarse una imagen")
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

     return new Producto(this.id, this.nombre, null,null, rutaImg,
             this.precioCompra, this.precioVenta,0,0,0);
  }

}
