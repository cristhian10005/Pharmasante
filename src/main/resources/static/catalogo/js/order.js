window.onload =listaPedido;

var calificacionProducto ={
    "calificacion": 0,
    "idProducto": 0,
    "idUsuario": 0
}
let btnPedido = document.querySelectorAll(".btn-p");

let order = document.querySelectorAll(".order-class");

let btn = btnPedido[0].classList.add("color-btn");
btn = btnPedido[0].getElementsByTagName("a")[0].classList.remove("btn-a");
btn = btnPedido[0].getElementsByTagName("a")[0].classList.add("btn-select");

btnPedido[0].addEventListener("click", ()=>{
	showElement(0);
});

btnPedido[1].addEventListener("click", ()=>{
	showElement(1);
});
btnPedido[2].addEventListener("click", ()=>{
	showElement(2);
});
async function listaPedido() {
    const urlParams = new URLSearchParams(window.location.search);
    const data = urlParams.get('encript');
    let dato = JSON.parse(atob(data));
    if(dato !=null){
        setData(dato);
        calificacionProducto.idUsuario = dato.idCliente;
        let tablaPedido = document.getElementById("tabla-pedido");
		let tablaPedido2 = document.getElementById("tabla-pedido2");
        let tablaCalificados = document.getElementById("id-qualify");

        const request = await fetch('../pedidos/listapedido', {
            method: 'POST',
            body: JSON.stringify(dato),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        });

        if(request.ok){
            const catalogoJson = await request.json();
            let carritolist = '';
            let carritolist2 = '';
    
            for (let produc of catalogoJson.recogerEnTiendas) {
                carritolist +=  `<tr>
				<td>${produc.pedido.id}</td>
				<td>${produc.pedido.fechaSolicitud}</td>
				<td>$ ${produc.pedido.precioPedido}</td>
				<td>${produc.pedido.estado.descripcion}</td>`;
              carritolist +=  produc.pedido.estado.id == 2? `<td>Por asignar</td>`:`<td>${produc.fecha_limite}</td>`;
                
            if(produc.pedido.estado.id == 2 ||produc.pedido.estado.id == 3 || produc.pedido.estado.id == 4){
                carritolist += `<td><a href="#" onclick="enviarDatoEliminarPedido(${produc.pedido.id})">
                <i class="fa-regular fa-circle-xmark"></i></a></td>`;
            }
            carritolist += `</tr>`;
        }
		for (let produc of catalogoJson.domicilios) {
			carritolist2 +=  `<tr>
			<td>${produc.pedido.id}</td>
			<td>${produc.pedido.fechaSolicitud}</td>
			<td>$ ${produc.pedido.precioPedido}</td>
			<td>${produc.pedido.estado.descripcion}</td>`;
            carritolist2 += produc.pedido.estado.id == 2? `<td>Por asignar</td>`
                            :`<td>${produc.fehcaLlegada} ${produc.horaLlegada}</td>`;
            if(produc.pedido.estado.id == 2 ||produc.pedido.estado.id == 3 || produc.pedido.estado.id == 4){
                carritolist2 += `<td><a href="#" onclick="enviarDatoEliminarPedido(${produc.pedido.id})">
                <i class="fa-regular fa-circle-xmark"></i></a></td>`;
            }
            carritolist2 += `</tr>`;
	}
        let calificados = `<div class="qualify">`;
        let contador = 0;
        for(let produc of catalogoJson.productos){
            if(contador > 2 && contador%3 ===0){
                calificados +=`</div><div class="qualify">`;
            }  
            calificados += `<ul class="qualify-section">
            <li><img src="../${produc.imagen}" alt=""></li>
            <li>
                <p>${produc.nombre}</p>
                <p>$ ${produc.precioVenta}</p>
            </li>
            <li>
                <p>Calificar</p>`;
                let puntaje = 1;
                for(let i =0; i<5;i++){
                    if(i<parseInt(produc.calificacion)){
                       calificados += `<a href="#" onclick = "calificarProducto(${produc.idProducto},${puntaje})">
                       <i class="fa-solid fa-star active"></i></a>`;
                    }else{
                        calificados += `<a href="#" onclick = "calificarProducto(${produc.idProducto},${puntaje})">
                        <i class="fa-solid fa-star disable"></i></a>`;    
                    }
                    puntaje++;
                }    
            calificados += `</li>
        </ul>`;
        contador++;
        }
        calificados += `</div>`
		tablaPedido.innerHTML = carritolist;
		tablaPedido2.innerHTML = carritolist2;
        tablaCalificados.innerHTML = calificados;
 	 }
    }
    iniciarBotones(dato);
}


function showElement(indice){
		order.forEach((element)=>{
		if (!element.classList.contains("section-off")) element.classList.add("section-off");
	});
		order[indice].classList.remove("section-off");

		btnPedido.forEach((ele)=>{
			if (ele.classList.contains("color-btn")) {
				ele.classList.remove("color-btn");
				ele.getElementsByTagName("a")[0].classList.add("btn-a");
				ele.getElementsByTagName("a")[0].classList.remove("btn-select");
			}
		});
		btnPedido[indice].classList.add("color-btn");
		btnPedido[indice].getElementsByTagName("a")[0].classList.remove("btn-a");
   		btnPedido[indice].getElementsByTagName("a")[0].classList.add("btn-select");

}

async function eliminarPedido(idPedido){
	dato = idPedido;
	let condfirmado = false;
    await Swal.fire({
        title: 'Seguro',
        text: "Desea eliminar el producto",
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
            location. reload();
      }
}

async function calificarProducto(idProducto, calificacion){
    calificacionProducto.idProducto = idProducto;
    calificacionProducto.calificacion = calificacion;

    const request = await fetch('../pedidos/calificar', {
        method: 'POST',
        body: JSON.stringify(calificacionProducto),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    });

    await Swal.fire(
        'Calificado',
        'Producto calificado con exito',
        'success'
    );
    listaPedido();
    showElement(2);

}

