package com.pharmasante.pharmasanteProyect.services;

import com.pharmasante.pharmasanteProyect.EntitiesDto.UsuarioEntradaDto;
import org.springframework.validation.Errors;

import com.pharmasante.pharmasanteProyect.models.Usuario;

public interface IUsuarioService {
    Usuario buscarUsuario(int idUsuario);
    void agregarUsuario(Usuario usuario, Errors errors);
    UsuarioEntradaDto validarUsuario(String usuario, String pass);
}
