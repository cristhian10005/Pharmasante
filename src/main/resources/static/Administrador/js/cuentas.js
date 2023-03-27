window.onload =listaPedido;
var catalogoJson = [];

async function listaPedido() {  
    let tablaProducto = document.getElementById("tabla-producto"); 

        const request = await fetch('../usuario/clientes', {
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
                <td>${produc.usuario.id}</td>
                <td>${produc.usuario.nombres} ${produc.usuario.apellidos}</td>
                <td>${produc.reportes}</td>
                <td>
				<a onclick="bloquear(${produc.usuario.id})"><i class="fa-solid fa-triangle-exclamation"></i></a>
					</td></tr>`;
            }
            tablaProducto.innerHTML = carritolist;
           }          
 }
 async function bloquear(id){
    const request = await fetch('../usuario/cambiar/'+id, {
        method: 'PUT',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    });

    if(request.ok){
        await Swal.fire(
            'Bloqueado',
            'Cliente bloqueado con exito',
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
  
    
