package com.restapi.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.restapi.api.entitys.Juego;
import com.restapi.api.entitys.Mensaje;
import com.restapi.api.repositorio.JuegoRepositorio;

@Service
public class JuegoService {

    @Autowired
	private JuegoRepositorio repo;

    public ResponseEntity<Mensaje> save(Juego juego) {
		Optional<Juego> posibleJuego = repo.findById(juego.getId());
		if (posibleJuego.isPresent()) {
			return new ResponseEntity<>(new Mensaje("El juego ya con id " + posibleJuego.get().getId() + " existe"), HttpStatus.CONFLICT);
		} else {
			repo.save(juego);
			return new ResponseEntity<>(new Mensaje("Se ha añadido el juego con id " + juego.getId()), HttpStatus.OK);
		}
    }

    public List<Juego> readAllByTitleOrDeveloper(String title, String developer) {
        if (developer == null && title == null) {
			return repo.findAll();
		} else if (title != null) {
			return repo.findByTitleContaining(title);
		} else { 
			return repo.findByDeveloperContaining(developer);
		}
    }

    public ResponseEntity<?> readByTitleOrDeveloper(String title, String developer) {
        if (developer == null && title == null) {											
			return new ResponseEntity<>(new Mensaje("No se han presentado parametros."), HttpStatus.NO_CONTENT);
		} else if (title != null) { 
			Optional<Juego> posibleJuego = repo.findFirstByTitleLike(title);
			if (posibleJuego.isPresent()) {
				return new ResponseEntity<>(posibleJuego.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new Mensaje("El juego con titulo " + title + " no existe."), HttpStatus.NOT_FOUND);
			}
		} else {
			Optional<Juego> posibleJuego =  repo.findFirstByDeveloperLike(developer);
			if (posibleJuego.isPresent()) {
				return new ResponseEntity<>(posibleJuego.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new Mensaje("El desarrollador con nombre " + developer + " no existe."), HttpStatus.NOT_FOUND);
			}
		}
    }

    public ResponseEntity<?> readByID(Integer idJuego) {
        Optional<Juego> posibleProducto = repo.findById(idJuego);
		if (posibleProducto.isPresent()) {
			return new ResponseEntity<>(posibleProducto.get(), HttpStatus.OK);
		} else { // Que pasa si no existe
			return new ResponseEntity<>(new Mensaje("El juego con id: " + idJuego + " no existe."), HttpStatus.NOT_FOUND);
		}
    }

    public ResponseEntity<Mensaje> updateByID(Integer idJuego, Juego juego) {
        Optional<Juego> posibleJuego = repo.findById(idJuego);
		if (posibleJuego.isPresent()) {
			repo.delete(posibleJuego.get());
			repo.save(juego);
			return new ResponseEntity<>(new Mensaje("El juego fue actualizado."), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new Mensaje("El juego con id " + idJuego + " no existe."), HttpStatus.NOT_FOUND);
		}
    }

    public ResponseEntity<Mensaje> deleteByID(Integer idJuego) {
        Optional<Juego> posibleJuego = repo.findById(idJuego);
		if (posibleJuego.isPresent()) {
			repo.deleteById(idJuego);
			return new ResponseEntity<>(new Mensaje("Se ha borrado el juego con id: " + idJuego), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new Mensaje("El juego con id " + idJuego + " no existe."), HttpStatus.NOT_FOUND);
		}
    }

}