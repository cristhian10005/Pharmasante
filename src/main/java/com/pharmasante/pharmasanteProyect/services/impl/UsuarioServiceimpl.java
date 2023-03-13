package com.pharmasante.pharmasanteProyect.services.impl;

import com.pharmasante.pharmasanteProyect.Excepciones.ProductException;
import com.pharmasante.pharmasanteProyect.models.Usuario;
import com.pharmasante.pharmasanteProyect.repository.IUsuarioRepository;
import com.pharmasante.pharmasanteProyect.services.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UsuarioServiceimpl implements IUsuarioService {
    @Autowired
    IUsuarioRepository usuarioRepository;

    @Override
    public Usuario buscarUsuario(int idUsuario) {
        Optional<Usuario>busqueda = usuarioRepository.findById(idUsuario);
        if (busqueda.isPresent()){
            return busqueda.get();
        }else {
            throw new ProductException("Ususario no encontrado", "Credenciales invalidas");
        }
    }
}
