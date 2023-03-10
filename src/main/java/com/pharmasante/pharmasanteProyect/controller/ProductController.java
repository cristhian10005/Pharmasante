package com.pharmasante.pharmasanteProyect.controller;

import com.pharmasante.pharmasanteProyect.models.Producto;
import com.pharmasante.pharmasanteProyect.EntitiesDto.ProductoDTO;
import com.pharmasante.pharmasanteProyect.services.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/inventario")
public class ProductController {
    @Autowired
    private IProductoService iproductoService;


    @GetMapping
    public List<Producto> productosPrincipales(){
        return iproductoService.listaProductos();
    }

    @PostMapping
    public ResponseEntity<Producto> productoGuardado(@Valid @RequestBody ProductoDTO pto, Errors errors)
    {
        return new ResponseEntity<>(iproductoService.guardarProducto(pto, errors), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void borrarProducto(@PathVariable int id){
         iproductoService.eliminarProducto(id);
    }

    @PutMapping
    public ResponseEntity<Producto> productoActualizado(@Valid @RequestBody ProductoDTO pto, Errors errors){
        return new ResponseEntity<>(iproductoService.guardarProducto(pto, errors), HttpStatus.OK);
    }
}
