package com.restapi.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.restapi.api.entitys.Mensaje;
import com.restapi.api.entitys.Usuario;
import com.restapi.api.repositorio.UsuarioRepositorio;

@Service
public class UsuarioService {

    @Autowired
	private UsuarioRepositorio repo;
	@Autowired
    private JavaMailSender mailSender;

    public ResponseEntity<Mensaje> save(Usuario usuario) {
        Optional<Usuario> posibleUsuario = repo.findByCorreo(usuario.getCorreo());
        if (posibleUsuario.isPresent()) {
			return new ResponseEntity<>(new Mensaje("El usuario con correo: " + usuario.getCorreo() + " ya existe"), HttpStatus.CONFLICT);
		} else { // Que pasa si no existe
			repo.save(usuario);
		    return new ResponseEntity<>(new Mensaje("Se ha añadido el usuario."), HttpStatus.OK);
		}
    }

    public ResponseEntity<?> read(String correoUsuario) {
        Optional<Usuario> posibleUsuario = repo.findByCorreo(correoUsuario);
		if (posibleUsuario.isPresent()) {
			return new ResponseEntity<>(posibleUsuario.get(), HttpStatus.OK);
		} else { // Que pasa si no existe
			return new ResponseEntity<>(new Mensaje("El usuario con correo: " + correoUsuario + " no existe"), HttpStatus.NOT_FOUND);
		}
    }

    public ResponseEntity<Mensaje> update(String correoUsuario, Usuario usuario) {
        Optional<Usuario> posibleUsuario = repo.findByCorreo(correoUsuario);
		if (posibleUsuario.isPresent()) {
            repo.delete(posibleUsuario.get());
			repo.save(usuario);
			return new ResponseEntity<>(new Mensaje("El usuario fue actualizado."), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new Mensaje("El usuario con correo: " + correoUsuario + " no existe."), HttpStatus.NOT_FOUND);
		}
    }

	public ResponseEntity<Mensaje> delete(String correoUsuario) {
		Optional<Usuario> posibleUsuario = repo.findByCorreo(correoUsuario);
		if (posibleUsuario.isPresent()) {
            repo.delete(posibleUsuario.get());
			return new ResponseEntity<>(new Mensaje("Se ha borrado el usuario con id: " + correoUsuario), HttpStatus.OK);
		} else { // Que pasa si no existe
			return new ResponseEntity<>(new Mensaje("El usuario con correo: " + correoUsuario + " no existe"), HttpStatus.NOT_FOUND);
		}
	}

    public void lostPassword(String correoUsuario) throws NotFoundException {
        Optional<Usuario> userOptional = repo.findByCorreo(correoUsuario);
		if (userOptional.isPresent()) {
			Usuario user = userOptional.get();
			String password = user.getPassword();
			String messageBody = "Los datos de inicio de sesión para su cuenta son:\n\nContraseña: " + password;
			
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom("noreply@baeldung.com");
			message.setTo(correoUsuario);
			message.setSubject("Recuperacion de credenciales");
			message.setText(messageBody);
			mailSender.send(message);
		} else {
			throw new NotFoundException();
		}
    }

}
