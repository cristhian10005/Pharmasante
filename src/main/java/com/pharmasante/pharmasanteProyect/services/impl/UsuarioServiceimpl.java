package com.pharmasante.pharmasanteProyect.services.impl;

import com.pharmasante.pharmasanteProyect.EntitiesDto.PasswordDto;
import com.pharmasante.pharmasanteProyect.EntitiesDto.UsuarioEntradaDto;
import com.pharmasante.pharmasanteProyect.Excepciones.ProductException;
import com.pharmasante.pharmasanteProyect.models.Cliente;
import com.pharmasante.pharmasanteProyect.models.Usuario;
import com.pharmasante.pharmasanteProyect.repository.IClienteRepository;
import com.pharmasante.pharmasanteProyect.repository.IRolRepository;
import com.pharmasante.pharmasanteProyect.repository.IUsuarioRepository;
import com.pharmasante.pharmasanteProyect.services.IUsuarioService;
import com.pharmasante.pharmasanteProyect.services.IValidaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;

import java.util.List;
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
    @Autowired
    IRolRepository rolRepository;

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
        Usuario usuario = usuarioRepository.findById(idCliente).get();
       Cliente cliente = clienteRepository.findByUsuario(usuario).get(0);
        return cliente;
    }
    @Override
    public void agregarUsuario(Usuario usuario, Errors errors) {
    	validaciones.validacionDeErrores(errors);
        if(usuario.getRol().getId() ==1){
            usuario.setEstado(1);
            usuario.setRol(rolRepository.findById(1).get());
            usuarioRepository.save(usuario);
            Cliente cliente = new Cliente(null, usuario,0);
            clienteRepository.save(cliente);
        }else {
            usuario.setRol(rolRepository.findById(2).get());
            usuario.setEstado(0);
    	    usuarioRepository.save(usuario);

        }
    }
    @Override
    public UsuarioEntradaDto validarUsuario(String usuario, String pass){
        Usuario usuario1 = usuarioRepository.findByNombreUsuarioAndPassword(usuario, pass)
                .stream().findFirst().orElseThrow(()->{
                    throw new ProductException("Usuario o contraseña incorrectos","Autenticación");
                });
        if (usuario1.getEstado() == 0){
            throw new ProductException("Usuario deshabilitado","Autenticación");
        }
        UsuarioEntradaDto usuarioEntradaDto = new UsuarioEntradaDto();
        usuarioEntradaDto.setNombre(usuario1.getNombreUsuario());
        usuarioEntradaDto.setIdCliente(usuario1.getId());
        usuarioEntradaDto.setIdServicio(usuario1.getRol().getId());
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

    @Override
    public List<Usuario> usuarioList(int id){
        return usuarioRepository.findByRol(rolRepository.findById(id).get());
    }
}
