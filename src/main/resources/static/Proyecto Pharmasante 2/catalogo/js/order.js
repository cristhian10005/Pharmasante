
let btnPedido = document.querySelectorAll(".btn-p");

let order = document.querySelectorAll(".order-class");


let btn = btnPedido[0].classList.add("color-btn");
btn = btnPedido[0].getElementsByTagName("a")[0].classList.remove("btn-a");
btn = btnPedido[0].getElementsByTagName("a")[0].classList.add("btn-select");

btnPedido[0].addEventListener("click", ()=>{
	showElement(0);
});

btnPedido[1].addEventListener("click", ()=>{
	showElement(1);
});



function showElement(indice){
		order.forEach((element)=>{
		if (!element.classList.contains("section-off")) element.classList.add("section-off");
	});
		order[indice].classList.remove("section-off");

		btnPedido.forEach((ele)=>{
			if (ele.classList.contains("color-btn")) {
				ele.classList.remove("color-btn");
				ele.getElementsByTagName("a")[0].classList.add("btn-a");
				ele.getElementsByTagName("a")[0].classList.remove("btn-select");
			}
		});
		btnPedido[indice].classList.add("color-btn");
		btnPedido[indice].getElementsByTagName("a")[0].classList.remove("btn-a");
   		btnPedido[indice].getElementsByTagName("a")[0].classList.add("btn-select");

}



