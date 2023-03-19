
let barsBtn = document.querySelector(".btn-bars");
let headerList = document.querySelector(".header-list");
let menuList = document.querySelector(".menu-list");
let upDown1 = -300;

if (barsBtn!=null)barsBtn.addEventListener("click", UpDown)

function UpDown(){
	if (upDown1 === -300) upDown1 = 70;
	else upDown1 = -300;
	headerList.style.top = upDown1 + "px";
}


/*Scroll*/
const btnPass = document.querySelector(".btn-catalogo");
let menuCatalogo = document.getElementById("nav-catalogo");
if (btnPass != null) {
	btnPass.addEventListener("click", ()=>{
	menuCatalogo.classList.toggle("desplegar");
});
}
