
window.onload =filtroNombre;


async function filtroNombre() {

    const urlParams = new URLSearchParams(window.location.search);
    const dato = urlParams.get('nombre');

    if(dato !=null){
        let catalogo = document.querySelector(".categoria-filtro");
        
        const request = await fetch('catalogo/busqueda/'+dato, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        });
        const catalogoJson = await request.json();
        let contador= 0;
        let filtro = '<section class="main-catalog">';
        for (let produc of catalogoJson) {

            if(contador>4 && contador%5 ===0){
                filtro +='</section><section class="main-catalog">'
            }
            filtro += `	<div class="main-catalog-section1">
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
                                <button class="btn-catalog" onclick="agregar()">Agregar</button>
                            </div>
                        </div>`
        }
        filtro +='</section>';
        catalogo.innerHTML = filtro;
    }
   
}