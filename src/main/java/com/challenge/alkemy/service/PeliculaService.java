package com.challenge.alkemy.service;

import com.challenge.alkemy.dto.PeliculaResponseDto;
import com.challenge.alkemy.entity.Pelicula;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface PeliculaService {
    ResponseEntity<Object> savePelicula(Pelicula pelicula);

    Optional<Pelicula> fetchPeliculaById(Long peliculaId);

    List<PeliculaResponseDto> fetchPeliculaByTitulo(String titulo);

    List<PeliculaResponseDto> fetchMovies();

    List<Pelicula> fetchAllPeliculas();

    List<PeliculaResponseDto> fetchPeliculaByOrder(String orden);

    void deletePeliculaById(Long peliculaId);

    Object updatePelicula(Long peliculaId, Pelicula pelicula);
}
