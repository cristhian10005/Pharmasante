window.onload =listaPedido;
var catalogoJson = {};

async function listaPedido() {  
        let tablaProducto = document.getElementById("tabla-producto"); 

        const request = await fetch('../inventario/productos', {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        });

            catalogoJson = await request.json();
            let carritolist = '';
    
           if(catalogoJson != undefined && catalogoJson != null && catalogoJson.length>0){
            for (let produc of catalogoJson) {
                carritolist +=  `<tr>
                <td>${produc.idProducto}</td>
                <td>${produc.nombre}</td>
                <td>${produc.proveedor.nombre}</td>
                <td>$ ${produc.precioCompra}</td>
                <td>$ ${produc.precioVenta}</td>
                <td>${produc.categoria.descripcion}</td>
                <td>${produc.calificacion}</td>
                <td>
                <a href="#" onclick="eliminar(${produc.idProducto})"> 
                <i class="fa-solid fa-trash-can"></i></a>
                <a href="#" onclick ="actualizar(${produc.idProducto})">
                <i class="fa-solid fa-pen-to-square open-form"></i></a>
                </td></tr>`;
            }
            tablaProducto.innerHTML = carritolist;
           }

           //iniciarBotonesVendedor(dato);
}

async function cargarForm(id1 =0, id2 =0){
    let proveedor = document.getElementById("proveedor");
    let categoria = document.getElementById("categoria");
    let formulario = document.querySelector(".form-product");

    const request = await fetch('../inventario/proveedores', {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    });
    let cat ='';
    let prov ='';
    let items = await request.json();
    for(let item of items.categorias){
        cat +=`<option value="${item.codCategoria}">${item.descripcion}</option>`;
    }
    for(let item of items.proveedores){
        prov +=`<option value="${item.codProveedor}">${item.nombre}</option>`;
    }

    proveedor.innerHTML = prov;
    categoria.innerHTML = cat;
    categoria.selectedIndex = id1-1;
    proveedor.selectedIndex = id2-1;
    formulario.classList.remove("ocultar");
}



async function guardar(id = null){
    let nombre = document.getElementById("nombre");
    let proveedor = document.getElementById("proveedor");
    let categoria = document.getElementById("categoria");
    let pCompra = document.getElementById("pcompra");
    let pVenta = document.getElementById("pventa");
    let img = document.getElementById("file-upload");
    let reader = new FileReader();
        let pto = {
            "id": id,
            "nombre": nombre.value,
            "categoria": categoria.value,
            "proveedor": proveedor.value,
            "nombreImg": "",
            "precioCompra": pCompra.value, 
            "precioVenta": pVenta.value,
            "bytesImg": ""

        };

        if (img.files && img.files[0]) {
            let getFile = img.files[0];
            let bytes = await encodeFileAsBase64URL(getFile);
            pto["bytesImg"] = bytes.split(",")[1];
            pto["nombreImg"] = getFile["name"];
        }
        let request = await fetch('../inventario/producto', {
            method: 'POST',
            body: JSON.stringify(pto),
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if(request.ok){
           await Swal.fire({
                icon: 'success',
                title: 'Producto agregado'
              });
              location.reload();
        }else{
            let errorJSon = await request.json();
            Swal.fire({
                icon: 'error',
                title:'Campo mal diligenciado'
              }); 
        }   
}

async function encodeFileAsBase64URL(file) {
    return new Promise((resolve) => {
        const reader = new FileReader();
        reader.addEventListener('loadend', () => {
            resolve(reader.result);
        });
        reader.readAsDataURL(file);
    });
}


async function eliminar(id){
    await Swal.fire({
        title: 'Seguro',
        text: "Desea eliminar el producto",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Eliminar'
      }).then(async (result) => {
        if (result.isConfirmed) {
            let response = await fetch('../inventario/'+id, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            if(response.ok){
                await Swal.fire(
                        'Eliminado',
                        'Producto eliminado con exito',
                        'success'
                    );
                    location. reload();
              }
        }
      });
}

async function actualizar(id){
    let producto = catalogoJson.find(p => p.idProducto == id);
    document.getElementById("nombre").value = producto.nombre;
    document.getElementById("pcompra").value = producto.precioCompra;
    document.getElementById("pventa").value = producto.precioVenta;
    let img = document.getElementById("file-upload");
    cargarForm(producto.categoria.codCategoria, producto.proveedor.codProveedor);
    let guardarBtn = document.getElementById("btn-save");
    guardarBtn.onclick = function() {
        guardar(producto.idProducto);
      };
}