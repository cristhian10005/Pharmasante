window.onload =catalogoPrincipal;

async function catalogoPrincipal() {
   
    const urlParams = new URLSearchParams(window.location.search);
    const data = urlParams.get('encript');
    dato = JSON.parse(atob(data));

    if(dato !=null){
        setData(dato);
    let catalogo = document.querySelectorAll(".main-catalog");
    const request = await fetch('../catalogo/cliente', {
        method: 'POST',
        body: JSON.stringify(dato),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    });
    const catalogoJson = await request.json();
    let vendidos = '';
    let valorados = '';
    for (let produc of catalogoJson.catalogoIndex.masVendidos) {
        vendidos += `	<div class="main-catalog-section1">
                            <div><img src="../${produc.imagen}" alt=""></div>
                            <ul class="main-catalog-section1-item">
                                <li><p>${produc.nombre}</p></li>
                                <li><p>$${produc.precioVenta}</p></li>
                            </ul>
                            <div class="estrellas">
                            <div>`;
                            for(let i =0; i<5;i++){
                                if(i<parseInt(produc.calificacion)){
                                   vendidos += `<i class="fa-solid fa-star active"></i>`;
                                }else{
                                    vendidos += `<i class="fa-solid fa-star disable"></i>`;    
                                }
                            }    
                          vendidos+=`</div>
                            <button class="btn-catalog" onclick="agregar(${produc.idProducto})">Agregar</button>
                         </div>
                        </div>`
    }
    for (let produc of catalogoJson.catalogoIndex.mejorValorados) {
        dato.idServicio =produc.idProducto;
        valorados += `	<div class="main-catalog-section1">
                            <div><img src="../${produc.imagen}" alt=""></div>
                            <ul class="main-catalog-section1-item">
                                <li><p>${produc.nombre}</p></li>
                                <li><p>$${produc.precioVenta}</p></li>
                            </ul>
                            <div class="estrellas">
                            <div>`;
                            for(let i =0; i<5;i++){
                                if(i<parseInt(produc.calificacion)){
                                   valorados += `<i class="fa-solid fa-star active"></i>`;
                                }else{
                                    valorados += `<i class="fa-solid fa-star disable"></i>`;    
                                }
                            }    
                          valorados+=`</div>
                            <button class="btn-catalog" onclick="agregar(${produc.idProducto})">Agregar</button>
                         </div>
                        </div>`
    }
    catalogo[0].innerHTML = vendidos;
    catalogo[1].innerHTML = valorados;
    iniciarBotones(dato);
    accionProducto(dato);
    }
    
   
}
