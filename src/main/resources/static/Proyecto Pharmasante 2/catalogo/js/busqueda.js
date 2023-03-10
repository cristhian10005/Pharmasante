

let btnBuscar= document.getElementById("btn-buscar");
btnBuscar.addEventListener("click", ()=>{
    const palabra = document.getElementById("tex-busqueda");
    if(palabra.value !=null && palabra.value!=undefined && palabra.value!=''){
        location.href='busqueda.html?nombre='+palabra.value;     
    }else{
        alert("Dato incorrecto");
    }
});