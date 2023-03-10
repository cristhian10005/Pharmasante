package com.pharmasante.pharmasanteProyect.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "producto")
@AllArgsConstructor
@NoArgsConstructor
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Integer idProducto;

    @Column(name = "nombre")
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "id_proveedor")
    private Proveedor proveedor;

    @Column(name = "imagen")
    private String imagen;

    @Column(name = "precio_compra")
    private Integer precioCompra;

    @Column(name = "precio_venta")
    private Integer precioVenta;

    @Column(name = "calificacion")
    private Integer calificacion;

    @Column(name = "unidades_vendidas")
    private Integer undVendidas;

    @Column(name = "stock")
    private Integer stock;

}
