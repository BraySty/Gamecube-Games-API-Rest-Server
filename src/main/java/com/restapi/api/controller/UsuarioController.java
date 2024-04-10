package com.restapi.api.controller;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restapi.api.entitys.Mensaje;
import com.restapi.api.entitys.Usuario;
import com.restapi.api.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UsuarioController {

	private final UsuarioService usuarioService;

	// http://localhost:8088/api/v1/lostEmail/correo@email.com
	@PostMapping("/users/lostEmail/{correo}") 
	public void lostPassword(@PathVariable("correo") String correoUsuario) throws NotFoundException{
		usuarioService.lostPassword(correoUsuario);
		
	}

	// http://localhost:8088/api/v1/users/usuario
	/*
		{
			"nombre": "Murcia",
			"correo": "Murcia@gmail.es",
			"password": "1234"
		}
	*/
    @PostMapping("/users/usuario") 
	ResponseEntity<Mensaje> insertarUsuario(@RequestBody Usuario usuario) {
		return usuarioService.save(usuario);
	}

    // http://localhost:8088/api/v1/users/usuario/Marco@gmail.es
    @GetMapping("/users/usuario/{correo}") 
	ResponseEntity<?> getUsuario(@PathVariable("correo") String correoUsuario) {
		return usuarioService.read(correoUsuario);
	}

	// http://localhost:8088/api/v1/users/usuario/Marco@gmail.es
	/*
		{
			"nombre": "Marco",
			"correo": "Marco@gmail.com",
			"password": "1234"
		}
	*/
    @PutMapping("/users/usuario/{correo}") 
	ResponseEntity<Mensaje> actualizarJuego(@PathVariable("correo") String correoUsuario, @RequestBody Usuario usuario) {
		return usuarioService.update(correoUsuario, usuario);
	}

    // http://localhost:8088/api/v1/users/usuario/Marco@gmail.es
    @DeleteMapping("/users/usuario/{correo}") 
	ResponseEntity<Mensaje> borrarProducto(@PathVariable("correo") String correoUsuario) {
        return usuarioService.delete(correoUsuario);
	}

}
