window.onload =listaPedido;
var dato ={};
var termino ='';

async function listaPedido() {
   const urlParams = new URLSearchParams(window.location.search);
    const data = urlParams.get('encript');
    dato = JSON.parse(atob(data));
    if(dato !=null){
        let tablaPedido = document.getElementById("tabla-pedidos"); 
        let enlaceCaja = document.getElementById("detalle-caja");
        enlaceCaja.addEventListener("click",()=>{mostrarCaja(dato.idCliente)});


        const request = await fetch('../vendedor/productos'+termino, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        });

            let catalogoJson = await request.json();
            let carritolist = '';
    
           if(catalogoJson != undefined && catalogoJson != null && catalogoJson.length>0){
            for (let produc of catalogoJson) {
                carritolist +=  `<tr>
                <td>${produc.idProducto}</td>
                <td>${produc.nombre}</td>
                <td>${produc.proveedor.nombre}</td>
                <td>$ ${produc.precioVenta}</td>
                <td class="td5"><a href="#" onclick="agregarP(${produc.idProducto})">
                <i class="fa-solid fa-circle-plus"></i></a></td></tr>`;
            }
            tablaPedido.innerHTML = carritolist;
           }
        }
        iniciarBotonesVendedor(dato);
        
    }


async function agregarP(idProducto){
    dato.idServicio= idProducto;
    console.log(dato)
    const request = await fetch('../vendedor/agregar', {
        method: 'POST',
        body: JSON.stringify(dato),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    });

    if(request.ok){
        await Swal.fire({
            icon: 'success',
            title: 'Producto agregado'
          });

          location.reload();
    }else{
       let errorJSon = await request.json();
        Swal.fire({
            icon: 'error',
            title: errorJSon.message
          });
    } 
}




async function mostrarCaja(idUsuario){
    
    dato.idCliente= idUsuario;
    console.log(dato);
    const request = await fetch('../vendedor/caja', {
        method: 'POST',
        body: JSON.stringify(dato),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'  
        }
    });

    let registros ='';
    let foot ='';
    let tablaDetalle = document.getElementById("tabla-detalle");
    let tablaFoot = document.getElementById("tabla-foot");
    let tablas = document.querySelectorAll(".tabla");
    let close = document.querySelectorAll(".enlace-lista");

    let pedido = await request.json();


 if(pedido.detalle != undefined && pedido.detalle != null && pedido.detalle.length>0){
    for(let producto of pedido.detalle){
        registros += `<tr>
        <td>${producto.producto.idProducto}</td>
        <td>${producto.producto.nombre}</td>
        <td>
        <a href="#" onclick="unidades(${producto.id}, 'resta')"><i class="fa-solid fa-angle-left"></i></a>
                 ${producto.cantidad}
        <a href="#" onclick="unidades(${producto.id}, 'suma')"><i class="fa-solid fa-angle-right"></i></a>
        </td>
        <td>$ ${producto.producto.precioVenta}</td>
        <td>$ ${producto.subtotal}</td>
        <td class="eliminar"><a href="#"  onclick="eliminarUnd(${producto.id})">
        <i class="fa-regular fa-circle-xmark"></i></a></td></tr>`;
    }
    tablaDetalle.innerHTML = registros;
    foot +=`<tr>
        <td></td><td></td><td></td>
        <td>Total</td>
        <td>${pedido.precioPedido}</td>
        <td><button onclick="vender(${pedido.id})">Registrar venta</button></td> 
    </tr>`;

    tablaFoot.innerHTML = foot;   
 }


    close[0].classList.add("desactivar-t");
    close[1].classList.remove("desactivar-t");
    tablas[0].classList.add("desactivar-t");
    tablas[1].classList.remove("desactivar-t");
}


async function unidades(id, tipo){
    dato.idServicio = id;
    dato.busqueda =tipo;
    const request = await fetch('../vendedor/cantidad', {
        method: 'PUT',
        body: JSON.stringify(dato),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    });

   if(request.ok){
    mostrarCaja(dato.idCliente);
   }else{
    let errorJSon = await request.json();
        Swal.fire({
        icon: 'error',  
        title: errorJSon.message
        });
   }
}




async function eliminarUnd(id){
    dato.idServicio = id;
    let condfirmado = false;
    await Swal.fire({
        title: '¿Seguro?',
        text: "Desea eliminar el producto",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Eliminar'
      }).then((result) => {
        if (result.isConfirmed) {
            condfirmado = true;
               fetch('../pedidos/carritodel', {
                method: 'DELETE',
                body: JSON.stringify(dato),
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }
            });
        }
      })

      if(condfirmado){
        await Swal.fire(
                'Eliminado',
                'Producto eliminado con exito',
                'success'
            );
            mostrarCaja(dato.idCliente);
      }
}

function buscar(){
    const palabra = document.getElementById("tex-busqueda");
    if(palabra.value !=null && palabra.value!=undefined && palabra.value!=''){
        termino ="/"+palabra.value;
        listaPedido();
    }

}

async function vender(id){
    dato.idServicio = id;
    let estado = {
        idPedido: id,
        estado: 7,
        hora: "00:00",
        idUsuario: dato.idCliente 
    }

    const request = await fetch('../vendedor/asignar', {
        method: 'POST',
        body: JSON.stringify(estado),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    });

    if(request.ok){
        await Swal.fire(
            'Registrada',
            'Venta registrada con exito',
            'success'
        );
        location.reload();
       }else{
        let errorJSon = await request.json();
            Swal.fire({
            icon: 'error',  
            title: errorJSon.message
            });
       }

}