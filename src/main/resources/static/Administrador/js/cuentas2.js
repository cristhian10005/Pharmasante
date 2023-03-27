window.onload =listaPedido;
var catalogoJson = [];

async function listaPedido() {  
    let tablaProducto = document.getElementById("tabla-producto"); 

        const request = await fetch('../usuario/vendedores', {
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
                <td>${produc.nombres} ${produc.apellidos}</td>
                <td>
				<a onclick="bloquear(${produc.id})"><i class="fa-solid fa-triangle-exclamation"></i></a>
					</td></tr>`;
            }
            tablaProducto.innerHTML = carritolist;
           }          
 }
 async function bloquear(id){
    const request = await fetch('../usuario/cambiarV/'+id, {
        method: 'PUT',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    });

    if(request.ok){
        await Swal.fire(
            'Habilitado',
            'Vendedor habilitado con exito',
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
  
    
