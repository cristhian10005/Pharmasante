package com.pharmasante.pharmasanteProyect.services.impl;

import com.pharmasante.pharmasanteProyect.EntitiesDto.UsuarioEntradaDto;
import com.pharmasante.pharmasanteProyect.Excepciones.ProductException;
import com.pharmasante.pharmasanteProyect.models.Usuario;
import com.pharmasante.pharmasanteProyect.repository.IUsuarioRepository;
import com.pharmasante.pharmasanteProyect.services.IUsuarioService;
import com.pharmasante.pharmasanteProyect.services.IValidaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;
@Service
@Validated
public class UsuarioServiceimpl implements IUsuarioService {
    @Autowired
    IUsuarioRepository usuarioRepository;
    @Autowired
    IValidaciones validaciones;

    @Override
    public Usuario buscarUsuario(int idUsuario) {
        Optional<Usuario>busqueda = usuarioRepository.findById(idUsuario);
        if (busqueda.isPresent()){
            return busqueda.get();
        }else {
            throw new ProductException("Ususario no encontrado", "Credenciales invalidas");
        }
    }
    @Override
    public void agregarUsuario(Usuario usuario, Errors errors) {
    	validaciones.validacionDeErrores(errors);
    	usuarioRepository.save(usuario);
    }
    @Override
    public UsuarioEntradaDto validarUsuario(String usuario, String pass){
        Usuario usuario1 = usuarioRepository.findByNombreUsuarioAndPassword(usuario, pass)
                .stream().findFirst().orElseThrow(()->{
                    throw new ProductException("Usuario o contraseña incorrectos","Autenticación");
                });
        UsuarioEntradaDto usuarioEntradaDto = new UsuarioEntradaDto();
        usuarioEntradaDto.setNombre(usuario1.getNombreUsuario());
        usuarioEntradaDto.setIdCliente(usuario1.getId());
        return usuarioEntradaDto;
    }
}
