function iniciarBotonesVendedor(dato){

    let btnFiltro = document.querySelectorAll('.btn-cargar');
    btnFiltro[0].addEventListener('click',()=>navegacion(dato, 'pagosDirectos.html'));
    btnFiltro[1].addEventListener('click',()=>navegacion(dato, 'caja.html'));
    btnFiltro[2].addEventListener('click',()=>navegacion(dato, 'pedidos.html'));
    btnFiltro[3].addEventListener('click',()=>navegacion(dato, 'account.html'));
}
function navegacion(dato, url){
    dato.idServicio = 1;
    let encript = btoa(JSON.stringify(dato));
    location.href =url+'?encript='+encript;
}