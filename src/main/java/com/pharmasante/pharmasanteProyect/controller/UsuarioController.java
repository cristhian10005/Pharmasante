package com.pharmasante.pharmasanteProyect.controller;

import com.pharmasante.pharmasanteProyect.EntitiesDto.UsuarioEntradaDto;
import com.pharmasante.pharmasanteProyect.EntitiesDto.ValidarUsuario;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import com.pharmasante.pharmasanteProyect.models.Usuario;
import com.pharmasante.pharmasanteProyect.services.IUsuarioService;

import javax.validation.Valid;

@RestController
@RequestMapping("usuario")
public class UsuarioController {
	@Autowired
	IUsuarioService usuarioService;

	@PostMapping("/registro")
	public void registrarse(@Valid @RequestBody Usuario usuario, Errors errors) {
		usuarioService.agregarUsuario(usuario, errors);
	}
	@PostMapping("/iniciar")
	public UsuarioEntradaDto validarUsuario(@RequestBody ValidarUsuario usuario){
		return usuarioService.validarUsuario(usuario.getUsuario(), usuario.getPass());
	}

}
