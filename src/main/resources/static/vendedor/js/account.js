window.onload =consultarCuenta();
var id =0;

async function consultarCuenta() {

    const urlParams = new URLSearchParams(window.location.search);
    const data = urlParams.get('encript');
    let dato = JSON.parse(atob(data));
    if(dato !=null){
        const request = await fetch('../usuario/infoemp', {
            method: 'POST',
            body: JSON.stringify(dato),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        });

        const usuario = await request.json();
        let datosUsuario = document.querySelector(".section-date-1");

        let infoUsuario = `<p>Nombre: <span>${usuario.nombres} ${usuario.apellidos}</span></p>
        <p>Nombre de usuario: <span>${usuario.nombreUsuario}</span></p>
        <p>Número de identificación: <span>${usuario.identificacion}</span></p>
        <p>Número de contacto: <span>${usuario.ncontacto}</span></p>
        <p>Correo: <span>${usuario.correo}</span></p>   
        <a href="#form-section-date" class="btn-pass">Actualizar contraseña</a>`;
        datosUsuario.innerHTML = infoUsuario;
        id = usuario.id;
    }
    iniciarBotonesVendedor(dato);
}

let boton = document.getElementById("update");
boton.addEventListener("click", async ()=>{
    event.preventDefault();
    let passold = document.getElementById("passold");
    let pass1 = document.getElementById("pass1");
    let pass2 = document.getElementById("pass2");
    if(pass1.value != pass2.value){
        await  Swal.fire({
            icon: 'error',
            title: ' Las contraseñas no coinciden'
          });
          return;
    }
    let updatePass = {
        "password": pass1.value,
        "passwordOld": passold.value, 
        "idUsuario": id
    }
    
    const request2 = await fetch('../usuario/pass', {
        method: 'PUT',
        body: JSON.stringify(updatePass),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    });
    
    if(request2.ok){
        await Swal.fire({
             icon: 'success',
             title: 'Contraseña actualizada'
           });
     }else{
        let errorJSon = await request2.json();
         Swal.fire({
             icon: 'error',
             title: errorJSon.message
           });
     }  

});
