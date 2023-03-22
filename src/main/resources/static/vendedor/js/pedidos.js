window.onload =listaPedido;


async function listaPedido() {
   //const urlParams = new URLSearchParams(window.location.search);
    //const data = urlParams.get('encript');
    //let dato = JSON.parse(atob(data));
    let dato ={
        "idCliente": 2,
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

            const catalogoJson = await request.json();
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
 	 
        iniciarBotones(dato);
    }




