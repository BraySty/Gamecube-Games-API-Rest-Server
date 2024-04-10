package com.restapi.api.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restapi.api.entitys.Juego;

public interface JuegoRepositorio extends JpaRepository<Juego, Integer>{
    List<Juego> findByDeveloperContaining(String developer);
    List<Juego> findByTitleContaining(String title);
    Optional<Juego> findFirstByDeveloperLike(String developer);
    Optional<Juego> findFirstByTitleLike(String title);
    Optional<Juego> findByConcatFields(String text);
}
