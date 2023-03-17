package com.pharmasante.pharmasanteProyect.services.impl;

import com.pharmasante.pharmasanteProyect.Excepciones.ProductException;
import com.pharmasante.pharmasanteProyect.models.Producto;
import com.pharmasante.pharmasanteProyect.EntitiesDto.ProductoDTO;
import com.pharmasante.pharmasanteProyect.repository.IProductoRepository;
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
    @Override
    public List<Producto> listaProductos(){
        return iproductoRepository.findAll();
    }
    @Override
    public Producto guardarProducto(ProductoDTO productoDTO, Errors errors){
       validaciones.validacionDeErrores(errors);
        String ruta = storageService.store(productoDTO);
        Producto producto = productoDTO.productoDTOtoEntity(ruta);
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
