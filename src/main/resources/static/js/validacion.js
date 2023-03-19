
// function validar(){

// 	/*Devuelve una referencia al elemento por su ID.*/
// 	var nombre = document.getElementById("nombre").value;
// 	// var apellido = document.getElementById("apellido").value;
//     // var identificaion = document.getElementById("identificacion").value;
//     // var contacto = document.getElementById("contacto").value;
// 	// var correo = document.getElementById("correo").value;
//     // var direccion = document.getElementById("direccion").value;
// 	// var usuario = document.getElementById("usuario").value;
// 	// var contraseña1 = document.getElementById("contraseña1").value;
//     // var contraseña2 = document.getElementById("contraseña2").value;
//     // expresion1=/\w+@\w+\.+[a-z]/;
//     // expresion2=/^\d{7,10}$/;




// 	if(nombre == 0){
// 		alert("Debe escribir su Nombre");
// 		return false;
// 	}
//     // if(nombre.length>20){
//     //     alert("Escriba un nombre valido");
//     //     return false;
//     // }
//     // if(apellido == 0){
// 	// 	alert("Debe escribir su Apellido");
// 	// 	return false;
// 	// }
//     // if(identificaion == 0){
// 	// 	alert("Debe escribir su identificación");
// 	// 	return false;
// 	// }
//     // if(correo == 0){
// 	// 	alert("Debe escribir su Correo");
// 	// 	return false;
// 	// }
//     // if(!expresion1.test(correo)){
//     //     alert("El correo no es valido");
//     //     return false;
//     // }
//     // if(usuario == 0){
//     //     alert("Debe escribir su usuario");
//     //     return false;
//     // }

//     // if(contraseña == 0){
//     //     alert("Debe escribir su contaseña");
//     //     return false;
//     // }

//     // if(contacto == 0){
//     //     alert("Debe escribir su teléfono");
//     //     return false;
//     // }

//     // if(isNaN(contacto)){
//     //     alert("Debe escribir un numero");
//     //     return false;
//     // }

//     // if(!expresion2.test(teléfono)){
//     //     alert("El numero debe ser un fijo o celular");
//     //     return false;
//     // }

// }

// function login(){
//     var usu = document.getElementById("usu").value;
//     var pass = document.getElementById("pass").value;
//     var r1 = document.getElementById("r1").checked;
//     var r2 = document.getElementById("r2").checked;
//     //alert(r1 + " "+r2);
//     if(usu == 0){
//         Swal.fire({
//             icon: 'error',
//             title: 'Debe introducir un nombre de usuario'
//           })
// 		return false;
// 	}
//     if(pass == 0 ){
// 		Swal.fire({
//             icon: 'error',
//             title: 'Debe introducir una contraseña'
//           })
// 		return false;
// 	}
   
//     setTimeout(function(){document.location.href = "cuidado.html"},500);
// }



/*Busqueda por nombre*/

let btnBuscar= document.getElementById("btn-buscar");
btnBuscar.addEventListener("click", ()=>{
    const palabra = document.getElementById("tex-busqueda");
    if(palabra.value !=null && palabra.value!=undefined && palabra.value!=''){
        location.href='busqueda.html?nombre='+palabra.value;     
    }else{
        alert("Dato incorrecto");
    }
});