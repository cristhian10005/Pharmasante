window.onload =listaCarrito;

async function listaCarrito() {
    const urlParams = new URLSearchParams(window.location.search);
    const data = urlParams.get('encript');
    let dato = JSON.parse(atob(data));
    if(dato !=null){
        setData(dato);
        let talbaShop = document.getElementById("tabla-shop");
        let total = document.querySelector(".total");

        const request = await fetch('../../pedidos/carritolist', {
            method: 'POST',
            body: JSON.stringify(dato),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        });
        const catalogoJson = await request.json();
        let carritolist = '';
        for (let produc of catalogoJson.pedido.detalle) {
            carritolist +=  `<tr class="order-section">
            <td><img src="../${produc.producto.imagen}" alt=""></td>
            <td>${produc.producto.nombre}</td>
            <td>${produc.producto.precioVenta}</td>
            <td class="adicionar">
                <a href="#" onclick="unidades(${produc.id}, 'resta')"><i class="fa-solid fa-angle-left"></i></a>
                ${produc.cantidad}	
                <a href="#" onclick="unidades(${produc.id}, 'suma')"><i class="fa-solid fa-angle-right"></i></a>
            </td>
            <td>  ${produc.subtotal}	</td>
            <td class="eliminar"><a href="#"  onclick="eliminarUnd(${produc.id})">
            <i class="fa-regular fa-circle-xmark"></i></a></td>
        </tr> `;

    }
        total.innerHTML  = `<p>Total del pedido</p> <p>${catalogoJson.pedido.precioPedido}</p>`;
        talbaShop.innerHTML = carritolist;
    }
    iniciarBotones(dato);

}