let panel = document.querySelector(".menu-panel");
let span1= document.querySelectorAll(".menu-panel span");
let flecha = document.querySelectorAll(".fa-solid")[1];
function repeler(){
   if(panel.classList.contains("menu-plegado")){
        panel.classList.remove("menu-plegado");
        panel.classList.add("menu-desplegado");    
        span1.forEach((e)=>{
            e.classList.remove("ocultar");
        });
        flecha.classList.replace("fa-arrow-right", "fa-arrow-left");
   }else{
        panel.classList.remove("menu-desplegado");
        panel.classList.add("menu-plegado");    
        span1.forEach((e)=>{
            e.classList.add("ocultar");
        });
        flecha.classList.replace("fa-arrow-left", "fa-arrow-right");
   }
}


/* Tabla activa*/
let numFila =-1;
let filas=  document.querySelectorAll(".tabla tr");
for (let i = 0; i < filas.length; i++) {
    filas[i].addEventListener("click", function() {
        this.classList.toggle("seleccionada");
        if(numFila >=0){
            if(filas[numFila].classList.contains("seleccionada")){
                filas[numFila].classList.remove("seleccionada")
            }
        }
        numFila = i;
      });
}
 
function iniciarBotones(dato){

    let btnFiltro = document.querySelectorAll('.btn-cargar');
    btnFiltro[0].addEventListener('click',()=>navegacion(dato, 'cliente.html'));
    btnFiltro[1].addEventListener('click',()=>navegacion(dato, 'catalogo.html'));
    btnFiltro[2].addEventListener('click',()=>navegacion(dato, 'shopping.html'));
    btnFiltro[3].addEventListener('click',()=>navegacion(dato, 'order.html'));
    btnFiltro[4].addEventListener('click',()=>navegacion(dato, 'account.html'));
}

function navegacion(dato, url){
    dato.idServicio = 1;
    let encript = btoa(JSON.stringify(dato));
    location.href =url+'?encript='+encript;
}