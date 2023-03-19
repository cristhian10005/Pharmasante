
const btnSales = document.querySelectorAll(".btn-sales");

const sales = document.querySelectorAll(".sales");


btnSales[0].addEventListener("click", ()=>{
	showElement(0);
});

btnSales[1].addEventListener("click", ()=>{
	showElement(1);
});

btnSales[2].addEventListener("click", ()=>{
	showElement(2);
});

function showElement(indice){
		sales.forEach((element)=>{
		if (!element.classList.contains("section-off")) element.classList.add("section-off");
	});
		sales[indice].classList.remove("section-off");
}



