window.onload =listaPedido;
var catalogoJson = [];

async function listaPedido() {  
    let tablaProducto = document.getElementById("tabla-producto"); 

        const request = await fetch('../vendedor/ganancias', {
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
                carritolist +=  `<tr>
                <td>${produc.fecha}</td>
                <td>${produc.ingresosBrutos}</td>
                <td>${produc.ganancias}</td> </tr>`;
                fechas.push(produc.fecha);
                gananciaslst.push(produc.ganancias);
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



