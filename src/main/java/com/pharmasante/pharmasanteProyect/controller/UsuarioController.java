package com.pharmasante.pharmasanteProyect.controller;

import com.pharmasante.pharmasanteProyect.EntitiesDto.PasswordDto;
import com.pharmasante.pharmasanteProyect.EntitiesDto.UsuarioEntradaDto;
import com.pharmasante.pharmasanteProyect.EntitiesDto.ValidarUsuario;
import com.pharmasante.pharmasanteProyect.models.Cliente;
import com.pharmasante.pharmasanteProyect.repository.IClienteRepository;
import com.pharmasante.pharmasanteProyect.repository.IUsuarioRepository;
import com.pharmasante.pharmasanteProyect.services.IValidaciones;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import com.pharmasante.pharmasanteProyect.models.Usuario;
import com.pharmasante.pharmasanteProyect.services.IUsuarioService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	@Autowired
	IUsuarioService usuarioService;
	@Autowired
	IValidaciones validaciones;
	@Autowired
	IClienteRepository clienteRepository;
	@Autowired
	IUsuarioRepository usuarioRepository;

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
	@PostMapping("/infoemp")
	public Usuario informacionEmpleado(@RequestBody UsuarioEntradaDto usuario){
		return usuarioService.buscarUsuario(usuario.getIdCliente());
	}

	@PutMapping("/pass")
	public void updatePass(@Valid @RequestBody PasswordDto pass, Errors errors){

		usuarioService.cambioPass(pass, errors);

	}

	@GetMapping("vendedores")
	public List<Usuario>vendedores(){
		return usuarioService.usuarioList(2);
	}

	@GetMapping("clientes")
	public List<Cliente>clientes(){
		return clienteRepository.findAll();
	}
	@PutMapping("cambiar/{id}")
	public void cambiar(@PathVariable int id){
		Usuario usuario = usuarioService.buscarUsuario(id);
		usuario.setEstado(0);
		usuarioRepository.save(usuario);
	}
	@PutMapping("cambiarV/{id}")
	public void cambiarV(@PathVariable int id){
		Usuario usuario = usuarioService.buscarUsuario(id);
		usuario.setEstado(1);
		usuarioRepository.save(usuario);
	}
}
