package com.challenge.alkemy.service;

import com.challenge.alkemy.entity.Genero;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface GeneroService {
    Object fetchGenero();

    ResponseEntity<Object> saveGenero(Genero genero);

    Optional<Genero> findGeneroById(Long generoId);

    void deleteGeneroById(Long generoId);
}
