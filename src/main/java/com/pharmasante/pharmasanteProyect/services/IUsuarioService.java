package com.pharmasante.pharmasanteProyect.services;

import com.pharmasante.pharmasanteProyect.EntitiesDto.PasswordDto;
import com.pharmasante.pharmasanteProyect.EntitiesDto.UsuarioEntradaDto;
import com.pharmasante.pharmasanteProyect.models.Cliente;
import org.springframework.validation.Errors;

import com.pharmasante.pharmasanteProyect.models.Usuario;

import java.util.List;

public interface IUsuarioService {
    Usuario buscarUsuario(int idUsuario);
    void agregarUsuario(Usuario usuario, Errors errors);
    UsuarioEntradaDto validarUsuario(String usuario, String pass);
    Cliente buscarCliente(int idCliente);
    void cambioPass(PasswordDto passwordDto, Errors errors);
    List<Usuario> usuarioList(int id);
}
