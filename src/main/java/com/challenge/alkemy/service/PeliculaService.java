package com.challenge.alkemy.service;

import com.challenge.alkemy.entity.dto.peliculaDto.request.CreatePeliculaRequestDto;
import com.challenge.alkemy.entity.dto.peliculaDto.request.UpdatePeliculaRequestDto;
import com.challenge.alkemy.entity.dto.peliculaDto.response.DetallePeliculaResponseDto;
import com.challenge.alkemy.entity.dto.peliculaDto.response.PeliculaBuscadaPorParametroResponseDto;
import com.challenge.alkemy.error.genero.GeneroNotFoundException;
import com.challenge.alkemy.error.pelicula.PeliculaAlreadyExistsException;
import com.challenge.alkemy.error.pelicula.PeliculaBuscadaPorParametroIncorrectoException;
import com.challenge.alkemy.error.pelicula.PeliculaNotFound;
import com.challenge.alkemy.error.personaje.PersonajeNotFoundException;
import com.challenge.alkemy.error.personaje.PersonajeNotFoundInPeliculaException;
import com.challenge.alkemy.error.personaje.PersonajeYaEnUsoException;

import java.util.List;

public interface PeliculaService {
    DetallePeliculaResponseDto createPelicula(CreatePeliculaRequestDto pelicula) throws PersonajeNotFoundException, PeliculaAlreadyExistsException, GeneroNotFoundException;

    DetallePeliculaResponseDto fetchPeliculaById(Long peliculaId) throws PeliculaNotFound;

    PeliculaBuscadaPorParametroResponseDto fetchPeliculaByTitulo(String titulo) throws PeliculaNotFound;

    List<PeliculaBuscadaPorParametroResponseDto> fetchPeliculasSinParametros() throws PeliculaNotFound;

    List<DetallePeliculaResponseDto> fetchAllPeliculas() throws PeliculaNotFound;

    List<PeliculaBuscadaPorParametroResponseDto> fetchPeliculasByOrder(String orden) throws PeliculaNotFound, PeliculaBuscadaPorParametroIncorrectoException;

    List<PeliculaBuscadaPorParametroResponseDto> fetchPeliculasByGeneroId(Long generoId) throws GeneroNotFoundException;

    void deletePeliculaById(Long peliculaId) throws Exception;

    DetallePeliculaResponseDto updatePelicula(Long peliculaId, UpdatePeliculaRequestDto peliculaRequest) throws PeliculaNotFound, PersonajeNotFoundException, PeliculaAlreadyExistsException;

    DetallePeliculaResponseDto agregarPersonajeToPelicula(Long idMovie, Long idCharacter) throws PeliculaNotFound, PersonajeNotFoundException, PersonajeYaEnUsoException;

    DetallePeliculaResponseDto eliminarPersonajeDePelicula(Long idMovie, Long idCharacter) throws PersonajeNotFoundException, PeliculaNotFound, PersonajeNotFoundInPeliculaException;
}
