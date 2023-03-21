var dataBtn ={};
function setData(dato){
    dataBtn = dato;
}

async function agregar(id){
    dataBtn.idServicio = id;
   try{
    const request = await fetch('../pedidos/carritoadd', {
        method: 'POST',
        body: JSON.stringify(dataBtn),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    });
    if(request.ok){ 
        Swal.fire({
            icon: 'success',
            title: 'Producto agregado'
          });
    }else{
        let error = await request.json();
        throw new Error(error.message);
    }
   }catch(error){
    Swal.fire({
        icon: 'warning',
        title: error,
        text: 'Para agregar más unidades de este producto, dirígete al carrito de compra'
      });
   }

}

function enlazar(id, dato){
    dato.idServicio = id+1;
    const encript = btoa(JSON.stringify(dato));
    location.href ='catalogo.html?encript='+encript;   
}

function buscar(dato){
    const palabra = document.getElementById("tex-busqueda");
    if(palabra.value !=null && palabra.value!=undefined && palabra.value!=''){
        dato.busqueda = palabra.value;
        let encript = btoa(JSON.stringify(dato));
        location.href='busqueda.html?encript='+encript;
    }else{
        alert("Dato incorrecto");
    }

}
function cargarCategoria(dato, url){
    dato.idServicio = 1;
    let encript = btoa(JSON.stringify(dato));
    location.href =url+'?encript='+encript;
}


function accionProducto(dato){

    let btnBuscar= document.getElementById("btn-buscar");
    btnBuscar.addEventListener("click", ()=>buscar(dato));
}

async function unidades(id, tipo){
    dataBtn.idServicio = id;
    dataBtn.busqueda =tipo;
    const request = await fetch('../pedidos/carritound', {
        method: 'PUT',
        body: JSON.stringify(dataBtn),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    });

   if(request.ok){
    location. reload();
   }else{
    let errorJSon = await request.json();
        Swal.fire({
        icon: 'error',  
        title: errorJSon.message
        });
   }
}
async function eliminarUnd(id){
    dataBtn.idServicio = id;
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
                body: JSON.stringify(dataBtn),
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
            location. reload();
      }
}



async function enviarDatoEliminarPedido(idPedido){
    dataBtn.idServicio= idPedido;
    let condfirmado = false;
    await Swal.fire({
        title: '¿Seguro?',
        text: "Desea eliminar el pedido",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Eliminar'
      }).then((result) => {
        if (result.isConfirmed) {
            condfirmado = true;
               fetch('../pedidos/eliminarPedido', {
                method: 'DELETE',
                body: JSON.stringify(dataBtn),
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
                'Pedido eliminado con exito',
                'success'
            );
            location. reload();
      }

}