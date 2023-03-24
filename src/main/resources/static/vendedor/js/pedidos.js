window.onload =listaPedido;
var catalogoJson ={};

async function listaPedido() {
   //const urlParams = new URLSearchParams(window.location.search);
    //const data = urlParams.get('encript');
    //let dato = JSON.parse(atob(data));
    let dato ={
        "idCliente": 3,
        "nombre": "Cristhian",
        "idServicio": 0,
        "busqueda": ""
    };
    if(dato !=null){
        let tablaPedido = document.getElementById("tabla-pedidos"); 

        const request = await fetch('../vendedor', {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        });

            catalogoJson = await request.json();
            let carritolist = '';
    
            for (let produc of catalogoJson) {
                carritolist +=  `<tr>
                <td>${produc.id}</td>
                <td>${produc.tipoPedido.descripcion}</td>
                <td>${produc.estado.descripcion}</td>
                <td>$ ${produc.precioPedido}</td>
                <td>${produc.usuario.nombres}</td>
                <td><a href="detallePedido.html">Ver más</a></td>
            </tr>`;
            }
            tablaPedido.innerHTML = carritolist;
        }
        iniciarBotonesVendedor(dato);
        
    }

function mostrarDetalle(idPedido){
    let detalle = catalogoJson.find(pedido => pedido.id == idPedido);
    let registros ='';
    let foot ='';
    let tablaDetalle = document.getElementById("tabla-detalle");
    let tablaFoot = document.getElementById("tabla-foot");
    for(let producto of detalle.detalle){
        registros += `<tr>
        <td>${producto.producto.idProducto}</td>
        <td>${producto.producto.nombre}</td>
        <td>$ ${producto.producto.precioVenta}</td>
        <td>${producto.cantidad}</td>
        <td>$ >${producto.subtotal}</td>
    </tr>`;
    }   
}




