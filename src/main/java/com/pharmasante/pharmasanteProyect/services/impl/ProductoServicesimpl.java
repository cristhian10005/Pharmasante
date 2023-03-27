package com.pharmasante.pharmasanteProyect.services.impl;

import com.pharmasante.pharmasanteProyect.Excepciones.ProductException;
import com.pharmasante.pharmasanteProyect.models.Categoria;
import com.pharmasante.pharmasanteProyect.models.Producto;
import com.pharmasante.pharmasanteProyect.EntitiesDto.ProductoDTO;
import com.pharmasante.pharmasanteProyect.models.Proveedor;
import com.pharmasante.pharmasanteProyect.repository.ICategoriaRepository;
import com.pharmasante.pharmasanteProyect.repository.IProductoRepository;
import com.pharmasante.pharmasanteProyect.repository.IProveedorRepository;
import com.pharmasante.pharmasanteProyect.services.IStorageService;
import com.pharmasante.pharmasanteProyect.services.IProductoService;
import com.pharmasante.pharmasanteProyect.services.IValidaciones;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
@AllArgsConstructor
public class ProductoServicesimpl implements IProductoService {

    private IProductoRepository iproductoRepository;
    private IValidaciones validaciones;
    private IStorageService storageService;
    private ICategoriaRepository categoriaRepository;
    private IProveedorRepository proveedorRepository;
    @Override
    public List<Producto> listaProductos(){
        return iproductoRepository.findAll();
    }
    @Override
    public Producto guardarProducto(ProductoDTO productoDTO, Errors errors){
       validaciones.validacionDeErrores(errors);
       String ruta;
        if(productoDTO.getId() !=null && productoDTO.getBytesImg().equals("")){
            Producto producto2 = iproductoRepository.findById(productoDTO.getId()).get();
            ruta = producto2.getImagen();
        }else {
            ruta = storageService.store(productoDTO);
        }
        Producto producto = productoDTO.productoDTOtoEntity(ruta);
        Categoria categoria = categoriaRepository.findById(productoDTO.getCategoria()).get();
        Proveedor proveedor = proveedorRepository.findById(productoDTO.getProveedor()).get();
        producto.setCategoria(categoria);
        producto.setProveedor(proveedor);
        if (producto.getPrecioCompra()>producto.getPrecioVenta()){
            throw new ProductException("El precio compra no puede exceder el precio venta","");
        }
        return iproductoRepository.save(producto);
    }
    @Override
    public void eliminarProducto(int id){
        iproductoRepository.delete(buscarProducto(id));
    }

    @Override
    public Producto buscarProducto(int id) {
        Optional<Producto> producto = iproductoRepository.findById(id);
        if (producto.isPresent()){
            return producto.get();
        }
        throw new ProductException("Parámetor invalido","No Existe el producto");
    }

    @Override
    public Producto actualizarProducto(ProductoDTO productoDTO,Errors errors){
        return guardarProducto(productoDTO, errors);
    }
}
