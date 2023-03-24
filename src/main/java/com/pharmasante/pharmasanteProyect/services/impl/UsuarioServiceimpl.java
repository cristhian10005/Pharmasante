package com.pharmasante.pharmasanteProyect.services.impl;

import com.pharmasante.pharmasanteProyect.EntitiesDto.PasswordDto;
import com.pharmasante.pharmasanteProyect.EntitiesDto.UsuarioEntradaDto;
import com.pharmasante.pharmasanteProyect.Excepciones.ProductException;
import com.pharmasante.pharmasanteProyect.models.Cliente;
import com.pharmasante.pharmasanteProyect.models.Usuario;
import com.pharmasante.pharmasanteProyect.repository.IClienteRepository;
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
    @Autowired
    IClienteRepository clienteRepository;

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
    public Cliente buscarCliente(int idCliente) {
        Optional<Cliente>busqueda = clienteRepository.findById(idCliente);
        if (busqueda.isPresent()){
            return busqueda.get();
        }else {
            throw new ProductException("Cliente no encontrado", "Credenciales invalidas");
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
    @Override
    public void cambioPass(PasswordDto passwordDto, Errors errors){
        validaciones.validacionDeErrores(errors);
        Usuario usuario = buscarUsuario(passwordDto.getIdUsuario());
        if(!usuario.getPassword().equals(passwordDto.getPasswordOld())){
            throw new ProductException("Contraseña incorrecta","");
        }
        usuario.setPassword(passwordDto.getPassword());
        usuarioRepository.save(usuario);
    }
}
