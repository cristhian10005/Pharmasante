const btnPedido = document.getElementById("btn-pedido");
const formPedido = document.querySelector(".domicilio");
const closeD = document.querySelector(".close-d");

btnPedido.addEventListener("click", ()=>{
    let elementoActivo = document.querySelector('input[id="radio1"]:checked');
    if(elementoActivo) {
        formPedido.classList.remove("domicilioDisplay");
    } 
});

closeD.addEventListener("click", ()=>{
    formPedido.classList.add("domicilioDisplay")
});
