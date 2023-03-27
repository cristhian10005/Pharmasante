package com.pharmasante.pharmasanteProyect.controller;

import com.pharmasante.pharmasanteProyect.EntitiesDto.AsignarDto;
import com.pharmasante.pharmasanteProyect.EntitiesDto.GananciasDto;
import com.pharmasante.pharmasanteProyect.EntitiesDto.UsuarioEntradaDto;
import com.pharmasante.pharmasanteProyect.models.Pedido;
import com.pharmasante.pharmasanteProyect.models.Producto;
import com.pharmasante.pharmasanteProyect.models.Ventas;
import com.pharmasante.pharmasanteProyect.repository.IVentasRepository;
import com.pharmasante.pharmasanteProyect.services.ICatalogoService;
import com.pharmasante.pharmasanteProyect.services.IPedidosService;
import com.pharmasante.pharmasanteProyect.services.IPedidosVendedorService;
import com.pharmasante.pharmasanteProyect.services.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/vendedor")
public class VendedorController {
    @Autowired
    IPedidosVendedorService pedidosVendedorService;
    @Autowired
    IProductoService productoService;
    @Autowired
    IPedidosService pedidosService;
    @Autowired
    ICatalogoService catalogoService;
    @Autowired
    IVentasRepository ventasRepository;

    @GetMapping("/pedidos")
    public List<Pedido> listaPedidos(){
        return pedidosVendedorService.listaPedidos();
    }
    @PostMapping("/asignar")
    public void asignarEstado(@RequestBody AsignarDto asignarDto){
        pedidosVendedorService.AsignarEstado(asignarDto.getIdPedido(), asignarDto.getEstado(),
                asignarDto.getHora(), asignarDto.getIdUsuario());
    }

    @GetMapping("/productos")
    public List<Producto> productos(){
        return productoService.listaProductos();
    }
    @GetMapping("/productos/{palabra}")
    public List<Producto> filtrarProductos(@PathVariable String palabra){
        return catalogoService.buscarProducto(palabra);
    }

    @PostMapping("/agregar")
    public void agregar(@RequestBody UsuarioEntradaDto usuario){
        pedidosService.pedidoInicial(usuario.getIdServicio(), usuario.getIdCliente(),3);
    }

    @PostMapping("/caja")
    public Pedido pedidoCaja(@RequestBody UsuarioEntradaDto usuario){
        return pedidosService.listaCarrito(usuario.getIdCliente());
    }
    @PutMapping("/cantidad")
    public void cantidadCaja(@RequestBody UsuarioEntradaDto usuario){
        pedidosService.adicionarUnidades(usuario.getIdServicio(), usuario.getBusqueda());
    }
    @DeleteMapping("/eliminar")
    public void eliminar(@RequestBody UsuarioEntradaDto usuarioEntradaDto) {
        pedidosService.eliminarProductoCarrito(usuarioEntradaDto.getIdServicio());
    }

    @GetMapping("ventas")
    public List<Ventas> ventas(){
        return ventasRepository.findAll();
    }
    @GetMapping("ganancias")
    public List<GananciasDto>gananciasDtos(){
        List<Ventas>ventasList = ventasRepository.findAll();
        List<GananciasDto>gananciasDtoList = new ArrayList<>();
        LocalDate fecha= ventasList.get(0).getFecha();
        GananciasDto g = new GananciasDto(0,0,fecha);
        int cont =1;
        for (Ventas venta : ventasList){
            if (venta.getFecha().equals(g.getFecha())){
                g.setGanancias(g.getGanancias() + venta.getGanancias());
                g.setIngresosBrutos(g.getIngresosBrutos() + venta.getPedido().getPrecioPedido());
                g.setFecha(venta.getFecha());
                if(ventasList.size()== cont){
                    gananciasDtoList.add(g);
                }
            }else {
                gananciasDtoList.add(g);
                g = new GananciasDto(venta.getPedido().getPrecioPedido(), venta.getGanancias(), venta.getFecha());
                if(ventasList.size()== cont){
                    gananciasDtoList.add(g);
                }
            }
            cont++;
        }
        return gananciasDtoList;
    }

    @GetMapping("vendidos")
    public List<Producto> vendidos(){
        return catalogoService.listaPrincipal().getMasVendidos();
    }
}
