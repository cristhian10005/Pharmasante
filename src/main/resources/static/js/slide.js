
const img1 = document.querySelector(".slide-item1");
const btns = document.querySelectorAll(".btns")
const btnMain = document.querySelectorAll(".main-btn");
const btnCatalogo = document.getElementById("btn-catalogo");
const containerCatalogo = document.querySelector(".menu-catalogo");
const quitar = document.querySelectorAll(".quitar");
let margiImg = 0;
let margiStyle = 0;

//Movimiento manual

let automatico = setInterval(()=>{
	margiStyle = margiStyle <=- 75 ? 0 : (margiStyle-25);
	btnMoveColor();
}, 4000);


//Movimiento manual

btns[0].addEventListener("click",()=>{
	margiStyle = 0;
	btnMoveColor();
	btns[0].classList.remove("color-principal");
	btns[0].classList.add("color-a");
	clearInterval(automatico)
});

btns[1].addEventListener("click",()=>{
	margiStyle = -25;
	btnMoveColor();
	btns[1].classList.remove("color-principal");
	btns[1].classList.add("color-a");
	clearInterval(automatico)
});
btns[2].addEventListener("click",()=>{
	margiStyle = -50;
	btnMoveColor();
	btns[2].classList.remove("color-principal");
	btns[2].classList.add("color-a");
	clearInterval(automatico)
});
btns[3].addEventListener("click",()=>{
	margiStyle = -75;
	btnMoveColor();
	btns[3].classList.remove("color-principal");
	btns[3].classList.add("color-a");
	clearInterval(automatico)
});


//funcion de botones

function btnMoveColor(){
	img1.style.marginLeft = margiStyle + "%";
	btns.forEach((element)=>{
		element.classList.add("color-principal");
	});
	btns.forEach((element)=>{
			if (element.classList.contains("color-a")){
			element.classList.remove("color-a");
		}
	})

}
