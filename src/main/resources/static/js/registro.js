
async function validarRegistro(){
    let nombreUsuario = document.getElementById("usuario");
    let nombres = document.getElementById("nombre");
    let apellidos = document.getElementById("apellido");
    let correo = document.getElementById("correo");
    let password = document.getElementById("contraseña1");
    let password2 = document.getElementById("contraseña2");
    let identificacion = document.getElementById("identificacion");
    let fechaNacimiento  = document.getElementById("fecha");
    let contacto = document.getElementById("contacto");
    let roles = document.getElementById("roles");
    
    usuario={
        "id": null,
        "nombreUsuario": nombreUsuario.value,
        "nombres": nombres.value,
        "apellidos": apellidos.value,
        "correo": correo.value,
        "password": password.value,
        "identificacion": identificacion.value,
        "fechaNacimiento": fechaNacimiento.value,
        "rol":{
            "id": roles.value,
            "descripcion": null
        },
        "ncontacto": contacto.value
    }

    if(password.value != password2.value){
        await  Swal.fire({
            icon: 'error',
            title: ' Las contraseñas no coinciden'
          });
          return;
    }
    const request = await fetch('usuario/registro', {
        method: 'POST',
        body: JSON.stringify(usuario),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    });

    if(request.ok){
        await Swal.fire({
             icon: 'success',
             title: 'Usuario registrado'
           });
     }else{
        let errorJSon = await request.json();
         Swal.fire({
             icon: 'error',
             title: errorJSon.message
           });
     }

 }

 async function validarUsuario(){
    let nombreUsuario = document.getElementById("usuario");
    let pass = document.getElementById("contraseña1");
    let usuario ={
        "usuario": nombreUsuario.value,
        "pass": pass.value
    }
    const request = await fetch('../../usuario/iniciar', {
        method: 'POST',
        body: JSON.stringify(usuario),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    });

    if(request.ok){
        let respuesta = await request.json();
        encript = btoa(JSON.stringify(respuesta));
        location.href ='catalogo/cliente.html?encript='+encript;
     }else{
        let errorJSon = await request.json();
         Swal.fire({
             icon: 'error',
             title: errorJSon.message
           });
     }

 }