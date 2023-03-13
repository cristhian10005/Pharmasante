package com.pharmasante.pharmasanteProyect.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Producto)) return false;
        Producto producto = (Producto) o;
        return Objects.equals(getIdProducto(), producto.getIdProducto());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdProducto());
    }
}
