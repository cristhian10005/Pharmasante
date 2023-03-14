function agregar(dato){
    let encript = btoa(JSON.stringify(dato));
    location.href ='shopping.html?encript='+encript;
}

function enlazar(id, dato){
    dato.idServicio = id+1;
    const encript = btoa(JSON.stringify(dato));
    location.href ='catalogo.html?encript='+encript;   
}

function buscar(dato){
    const palabra = document.getElementById("tex-busqueda");
    if(palabra.value !=null && palabra.value!=undefined && palabra.value!=''){
        dato.busqueda = palabra.value;
        let encript = btoa(JSON.stringify(dato));
        location.href='busqueda.html?encript='+encript;
    }else{
        alert("Dato incorrecto");
    }

}
function cargarCategoria(dato, url){
    dato.idServicio = 1;
    let encript = btoa(JSON.stringify(dato));
    location.href =url+'?encript='+encript;
}


function accionProducto(dato){

    let btnBuscar= document.getElementById("btn-buscar");
    btnBuscar.addEventListener("click", ()=>buscar(dato));

    let agregarShop = document.getElementById("agregar-shop");
    agregarShop.addEventListener("click",()=>agregar(dato));
}