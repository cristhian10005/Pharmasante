//Alerta de error
// Swal.fire({
//     icon: 'error',
//     title: 'Campo mal diligenciado',
//     text: 'El precio compra solo puede contener números',
//   });



  // Swal.fire({
  //   icon: 'success',
  //   title: 'Producto agregado'
  // })

// Swal.fire({
//     icon: 'warning',
//     title: 'Este producto ya ha sido registrado',
//     text: 'Para cambiar la cantidad dirijase a la caja'
//     html: 'Clic en <a href="Registro.html">Registrarse</a>'
//   })

Swal.fire({
  title: 'Seguro',
  text: "Desea eliminar el producto",
  icon: 'warning',
  showCancelButton: true,
  confirmButtonColor: '#3085d6',
  cancelButtonColor: '#d33',
  confirmButtonText: 'Eliminar'
}).then((result) => {
  if (result.isConfirmed) {
    Swal.fire(
      'Eliminado',
      'Producto eliminado con exito',
      'success'
    )
  }
})