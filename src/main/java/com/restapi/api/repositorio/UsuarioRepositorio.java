package com.restapi.api.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restapi.api.entitys.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, String>{

    Optional<Usuario> findByCorreo(String correo);
    
}
