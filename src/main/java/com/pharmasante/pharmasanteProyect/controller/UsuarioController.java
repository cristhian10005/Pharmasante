package com.pharmasante.pharmasanteProyect.controller;

import com.pharmasante.pharmasanteProyect.EntitiesDto.PasswordDto;
import com.pharmasante.pharmasanteProyect.EntitiesDto.UsuarioEntradaDto;
import com.pharmasante.pharmasanteProyect.EntitiesDto.ValidarUsuario;
import com.pharmasante.pharmasanteProyect.models.Cliente;
import com.pharmasante.pharmasanteProyect.services.IValidaciones;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import com.pharmasante.pharmasanteProyect.models.Usuario;
import com.pharmasante.pharmasanteProyect.services.IUsuarioService;

import javax.validation.Valid;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	@Autowired
	IUsuarioService usuarioService;
	@Autowired
	IValidaciones validaciones;

	@PostMapping("/registro")
	public void registrarse(@Valid @RequestBody Usuario usuario, Errors errors) {
		usuarioService.agregarUsuario(usuario, errors);
	}
	@PostMapping("/iniciar")
	public UsuarioEntradaDto validarUsuario(@RequestBody ValidarUsuario usuario){
		return usuarioService.validarUsuario(usuario.getUsuario(), usuario.getPass());
	}

	@PostMapping("/infocliente")
	public Cliente obtenerInformacion(@RequestBody UsuarioEntradaDto usuario){
		return  usuarioService.buscarCliente(usuario.getIdCliente());
	}
	@PutMapping("/pass")
	public void updatePass(@Valid @RequestBody PasswordDto pass, Errors errors){

		usuarioService.cambioPass(pass, errors);

	}

}
