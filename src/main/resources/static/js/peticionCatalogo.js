window.onload =catalogoPrincipal;

async function catalogoPrincipal() {
    let catalogo = document.querySelectorAll(".main-catalog");
    const request = await fetch('catalogo/index', {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    });
    const catalogoJson = await request.json();
    let vendidos = '';
    let valorados = '';
    for (let produc of catalogoJson.masVendidos) {
        vendidos += `	<div class="main-catalog-section1">
                            <div><img src="${produc.imagen}" alt=""></div>
                            <ul class="main-catalog-section1-item">
                                <li><p>${produc.nombre}</p></li>
                                <li><p>$${produc.precioVenta}</p></li>
                            </ul>
                            <div class="estrellas">
                                <div>
                                    <a href=""><i class="fa-solid fa-star"></i></a>
                                    <a href=""><i class="fa-solid fa-star"></i></a>
                                    <a href=""><i class="fa-solid fa-star"></i></a>
                                    <a href=""><i class="fa-solid fa-star"></i></a>
                                    <a href=""><i class="fa-solid fa-star"></i></a>
                                </div>
                            <button class="btn-catalog" onclick="agregar(${produc.idProducto})">Agregar</button>
                         </div>
                        </div>`
    }
    for (let produc of catalogoJson.mejorValorados) {
        valorados += `	<div class="main-catalog-section1">
                            <div><img src="${produc.imagen}" alt=""></div>
                            <ul class="main-catalog-section1-item">
                                <li><p>${produc.nombre}</p></li>
                                <li><p>$${produc.precioVenta}</p></li>
                            </ul>
                            <div class="estrellas">
                                <div>
                                    <a href=""><i class="fa-solid fa-star"></i></a>
                                    <a href=""><i class="fa-solid fa-star"></i></a>
                                    <a href=""><i class="fa-solid fa-star"></i></a>
                                    <a href=""><i class="fa-solid fa-star"></i></a>
                                    <a href=""><i class="fa-solid fa-star"></i></a>
                                </div>
                            <button class="btn-catalog" onclick="agregar(${produc.idProducto})">Agregar</button>
                         </div>
                        </div>`
    }
    catalogo[0].innerHTML = vendidos;
    catalogo[1].innerHTML = valorados;
}