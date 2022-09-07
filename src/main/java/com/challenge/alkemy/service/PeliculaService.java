package com.challenge.alkemy.service;

import com.challenge.alkemy.entity.Pelicula;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface PeliculaService {
    ResponseEntity<Object> savePelicula(Pelicula pelicula);

    Optional<Pelicula> fetchPeliculaById(Long peliculaId);
}
