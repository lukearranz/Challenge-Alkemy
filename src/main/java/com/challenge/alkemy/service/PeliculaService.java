package com.challenge.alkemy.service;

import com.challenge.alkemy.entity.dto.peliculaDto.request.CreatePeliculaRequestDto;
import com.challenge.alkemy.entity.dto.peliculaDto.response.CreatePeliculaResponseDto;
import com.challenge.alkemy.entity.dto.peliculaDto.response.PeliculaBuscadaPorParametroResponseDto;
import com.challenge.alkemy.entity.Pelicula;
import com.challenge.alkemy.error.genero.GeneroNotFoundException;
import com.challenge.alkemy.error.pelicula.PeliculaAlreadyExistsException;
import com.challenge.alkemy.error.pelicula.PeliculaNotFound;
import com.challenge.alkemy.error.personaje.PersonajeNotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface PeliculaService {
    CreatePeliculaResponseDto createPelicula(CreatePeliculaRequestDto pelicula) throws PersonajeNotFoundException, PeliculaAlreadyExistsException, GeneroNotFoundException;

    Optional<Pelicula> fetchPeliculaById(Long peliculaId);

    PeliculaBuscadaPorParametroResponseDto fetchPeliculaByTitulo(String titulo) throws PeliculaNotFound;

    List<PeliculaBuscadaPorParametroResponseDto> fetchPeliculasSinParametros();

    ResponseEntity<Object> fetchAllPeliculas();

    List<PeliculaBuscadaPorParametroResponseDto> fetchPeliculasByOrder(String orden);

    List<PeliculaBuscadaPorParametroResponseDto> fetchPeliculasByGeneroId(Long generoId);

    void deletePeliculaById(Long peliculaId) throws Exception;

    Object updatePelicula(Long peliculaId, Pelicula pelicula);

    ResponseEntity<Object> agregarPersonajeToPelicula(Long idMovie, Long idCharacter);

    ResponseEntity<Object> eliminarPersonajeDePelicula(Long idMovie, Long idCharacter);
}
