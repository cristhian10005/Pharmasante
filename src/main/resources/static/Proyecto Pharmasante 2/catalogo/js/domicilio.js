 function enviarPedido(dato, idPedido){
    const btnPedido = document.getElementById("btn-pedido");
    const formPedido = document.querySelector(".domicilio");
    const closeD = document.querySelector(".close-d");
    dato.idServicio = idPedido;
    btnPedido.addEventListener("click", async()=>{

        let elementoActivo = document.querySelector('input[id="domicilio-r"]:checked');
        let elementoActivo2 = document.querySelector('input[id="tienda-r"]:checked');
        if(elementoActivo) {
            formPedido.classList.remove("domicilioDisplay");
            let btnDomicilio = document.getElementById("btn-domicilio");
            let destinatario = document.getElementById("destinatario");
            let contacto = document.getElementById("contacto");
            let direccion = document.getElementById("direccion");
            
            btnDomicilio.addEventListener("click",async ()=>{
                let domicilio = {
                    "idCliente": dato.idCliente,
                    "idServicio": idPedido,
                    "destinatario": destinatario.value,
                    "contacto": contacto.value,
                    "direccion": direccion.value
                };
                const request = await fetch('../../pedidos/domicilio', {
                    method: 'POST',
                    body: JSON.stringify(domicilio),
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    }
                });
                if(request.ok){
                    await Swal.fire({
                         icon: 'success',
                         title: 'Domicilio solicitado'
                       });
                       location.reload();
                 }else{
                    let errorJSon = await request.json();
                     Swal.fire({
                         icon: 'error',
                         title: errorJSon.message
                       });
                 }
            });
            

        }else if(elementoActivo2) {

            const request = await fetch('../../pedidos/recoger', {
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
                    title: 'Pedido solicitado'
                  });
                  location.reload();
            }else{
                Swal.fire({
                    icon: 'error',
                    title: 'No dispone de productos en su carrito'
                  });
            }
        }else{
            Swal.fire({
                icon: 'error',
                title: 'No has seleccionado ningún tipo de pedido'
              });
        }
    });

    closeD.addEventListener("click", ()=>{
    formPedido.classList.add("domicilioDisplay")
});



}