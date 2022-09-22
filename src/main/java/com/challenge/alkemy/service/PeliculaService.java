package com.challenge.alkemy.service;

import com.challenge.alkemy.dto.PeliculaResponseDto;
import com.challenge.alkemy.entity.Pelicula;
import io.swagger.models.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface PeliculaService {
    ResponseEntity<Object> savePelicula(Pelicula pelicula);

    Optional<Pelicula> fetchPeliculaById(Long peliculaId);

    List<PeliculaResponseDto> fetchPeliculaByTitulo(String titulo);

    List<PeliculaResponseDto> fetchMovies();

    ResponseEntity<Object> fetchAllPeliculas();

    List<PeliculaResponseDto> fetchPeliculaByOrder(String orden);

    void deletePeliculaById(Long peliculaId);

    Object updatePelicula(Long peliculaId, Pelicula pelicula);

    ResponseEntity<Object> addCharacterToMovie(Long idMovie, Long idCharacter);
}
