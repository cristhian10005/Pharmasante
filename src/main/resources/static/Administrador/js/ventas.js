window.onload =listaPedido;
var catalogoJson = [];

async function listaPedido() {  
        let tablaProducto = document.getElementById("tabla-producto"); 

        const request = await fetch('../vendedor/ventas', {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        });

            catalogoJson = await request.json();
            let carritolist = '';
    
           if(catalogoJson != undefined && catalogoJson != null && catalogoJson.length>0){
            for (let produc of catalogoJson) {
                carritolist +=  `<tr>
                <td>${produc.id}</td>
                <td>${produc.pedido.tipoPedido.descripcion}</td>
                <td>${produc.fecha}</td>
                <td>$ ${produc.pedido.precioPedido}</td>
                <td>$ ${produc.ganancias}</td>
                <td>${produc.usuario.nombres}  ${produc.usuario.apellidos}</td>
                <td>
                <a href="#" onclick ="mostrarDetalle(${produc.id})">
                <i class="fa-solid fa-eye"></i></a>
                </td></tr>`;
            }
            tablaProducto.innerHTML = carritolist;
           }

           //iniciarBotonesVendedor(dato);
}
function mostrarDetalle(id){
    let detalle = catalogoJson.find(venta => venta.id == id);
    let registros ='';
    let tablaDetalle = document.getElementById("tabla-detalle");    
    let tablas = document.querySelectorAll(".tabla");
    let close = document.querySelector(".enlace-lista");
    console.log( detalle.pedido.detalle);
    for(let producto of detalle.pedido.detalle){
        registros += `<tr>
        <td>${producto.producto.idProducto}</td>
        <td>${producto.producto.nombre}</td>
        <td>$ ${producto.producto.precioVenta}</td>
        <td>${producto.cantidad}</td>
        <td>$ ${producto.subtotal}</td>
    </tr>`;
    }   
    tablaDetalle.innerHTML = registros;
    close.classList.remove("desactivar-t");
    tablas[0].classList.add("desactivar-t");
    tablas[1].classList.remove("desactivar-t");
}
