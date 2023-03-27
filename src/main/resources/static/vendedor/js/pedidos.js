window.onload =listaPedido;
var catalogoJson ={};

async function listaPedido() {
    const urlParams = new URLSearchParams(window.location.search);
    const data = urlParams.get('encript');
    let dato = JSON.parse(atob(data));

    if(dato !=null){
        let tablaPedido = document.getElementById("tabla-pedidos"); 

        const request = await fetch('../vendedor/pedidos', {
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
                <td><a href="#" onclick ="mostrarDetalle(${produc.id},${dato.idCliente})">Ver más</a></td>
            </tr>`;
            }
            tablaPedido.innerHTML = carritolist;
        }
        iniciarBotonesVendedor(dato);
        
    }

function mostrarDetalle(idPedido, idUsuario){
    let detalle = catalogoJson.find(pedido => pedido.id == idPedido);
    let registros ='';
    let foot ='';
    let tablaDetalle = document.getElementById("tabla-detalle");
    let tablaFoot = document.getElementById("tabla-foot");
    let tablas = document.querySelectorAll(".tabla");
    let close = document.querySelector(".enlace-lista");
    for(let producto of detalle.detalle){
        registros += `<tr>
        <td>${producto.producto.idProducto}</td>
        <td>${producto.producto.nombre}</td>
        <td>$ ${producto.producto.precioVenta}</td>
        <td>${producto.cantidad}</td>
        <td>$ ${producto.subtotal}</td>
    </tr>`;
    }   
    tablaDetalle.innerHTML = registros;
    foot +=`<tr>
        <td>Código: ${detalle.id}</td>
        <td>Solicitante: ${detalle.usuario.nombres} ${detalle.usuario.apellidos}</td>
        <td>${detalle.tipoPedido.descripcion}</td> 
        <td>${detalle.estado.descripcion}</td>
        <td></td>
    </tr>`;

    foot +=`<tr>
        <td>Total:</td>
        <td>$${detalle.precioPedido}</td>`;
              
    if(detalle.estado.id !=5 && detalle.estado.id !=6 && detalle.estado.id !=7){
        foot += `  <td>Asignar estado </td>
        <td><select name="" id="asignar-estado">`;
        if(detalle.estado.id == 2){
            if(detalle.tipoPedido.id == 1){
                foot +=`<option value="3">Alistado</option>`;
            }else{
                foot +=`<option value="4">Enviado</option>`;
            }
            foot+= `<option value="5">Rechazado</option>`;
        }else{
            foot+= `<option value="5">Rechazado</option>
                    <option value="6">Cancelado</option>
                    <option value="7">Entregado</option>`;
        }
        foot += `</select></td><td><button onclick= "asignarEstado(${detalle.id},${idUsuario})">Actualizar</button></td></tr>`;
    }else{
        foot += `<td></td><td></td><td></td></tr>`;
    }



    tablaFoot.innerHTML = foot;

    close.classList.remove("desactivar-t");
    tablas[0].classList.add("desactivar-t");
    tablas[1].classList.remove("desactivar-t");
}




async function asignarEstado(id, idcl){

    let seleccion = document.getElementById('asignar-estado');

    let estado = {
        idPedido: id,
        estado: seleccion.value,
        hora: '00:00',
        idUsuario: idcl
      }
  
      if(seleccion.value == 4){
        await Swal.fire({
            title: 'Ingrese la hora aproximada en la que llegará el pedido:',
            html: '<input type="time" step="300" value="00:00" id ="hora">',
            showCancelButton: true,
            confirmButtonText: 'Guardar',
            cancelButtonText: 'Cancelar',
          }).then((result) => {
            if (result.isConfirmed) {
                estado.hora = document.getElementById("hora").value;
            }else{
                return;
            }
          });
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
         await Swal.fire({
             icon: 'success',
             title: 'Estado asignado'
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