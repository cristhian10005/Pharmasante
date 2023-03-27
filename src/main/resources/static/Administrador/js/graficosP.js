window.onload =listaPedido;
var catalogoJson = [];

async function listaPedido() {  
    let tablaProducto = document.getElementById("tabla-producto"); 

        const request = await fetch('../vendedor/vendidos', {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        });

            catalogoJson = await request.json();
            let carritolist = '';
    
           if(catalogoJson != undefined && catalogoJson != null && catalogoJson.length>0){
            let fechas =[];
            let gananciaslst =[];
            for (let produc of catalogoJson) {
                let ga = (produc.precioVenta- produc.precioCompra)*produc.undVendidas;
                carritolist +=  `<tr>
                <td>${produc.idProducto}</td>
                <td>${produc.nombre}</td>
                <td>${produc.undVendidas}</td>
                <td>${ga}</td> </tr>`;
                fechas.push(produc.nombre);
                gananciaslst.push(produc.undVendidas);
            }

            let miCanva = document.getElementById("graficos").getContext("2d");
            var chart = new Chart(miCanva,{
                type: "bar",
                data: {
                    labels: fechas,
                    datasets:[{
                        label: 'Ganancias',
                        data: gananciaslst
                    }]
                }
            });


            tablaProducto.innerHTML = carritolist;
           }          
 }
    

           //iniciarBotonesVendedor(dato)



