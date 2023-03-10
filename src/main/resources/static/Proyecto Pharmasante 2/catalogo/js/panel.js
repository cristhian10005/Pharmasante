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
 
