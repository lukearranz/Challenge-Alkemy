package com.challenge.alkemy.service;

import com.challenge.alkemy.entity.dto.peliculaDto.request.CreatePeliculaRequestDto;
import com.challenge.alkemy.entity.dto.peliculaDto.request.UpdatePeliculaRequestDto;
import com.challenge.alkemy.entity.dto.peliculaDto.response.DetallePeliculaResponseDto;
import com.challenge.alkemy.entity.dto.peliculaDto.response.PeliculaBuscadaPorParametroResponseDto;
import com.challenge.alkemy.error.genero.GeneroNotFoundException;
import com.challenge.alkemy.error.pelicula.PeliculaAlreadyExistsException;
import com.challenge.alkemy.error.pelicula.PeliculaBuscadaPorParametroIncorrectoException;
import com.challenge.alkemy.error.pelicula.PeliculaNotFoundException;
import com.challenge.alkemy.error.personaje.PersonajeNotFoundException;
import com.challenge.alkemy.error.personaje.PersonajeNotFoundInPeliculaException;
import com.challenge.alkemy.error.personaje.PersonajeYaEnUsoException;

import java.util.List;

public interface PeliculaService {
    DetallePeliculaResponseDto createPelicula(CreatePeliculaRequestDto pelicula) throws PersonajeNotFoundException, PeliculaAlreadyExistsException, GeneroNotFoundException;

    DetallePeliculaResponseDto fetchPeliculaById(Long peliculaId) throws PeliculaNotFoundException;

    PeliculaBuscadaPorParametroResponseDto fetchPeliculaByTitulo(String titulo) throws PeliculaNotFoundException;

    List<PeliculaBuscadaPorParametroResponseDto> fetchPeliculasSinParametros() throws PeliculaNotFoundException;

    List<DetallePeliculaResponseDto> fetchAllPeliculas() throws PeliculaNotFoundException;

    List<PeliculaBuscadaPorParametroResponseDto> fetchPeliculasByOrder(String orden) throws PeliculaNotFoundException, PeliculaBuscadaPorParametroIncorrectoException;

    List<PeliculaBuscadaPorParametroResponseDto> fetchPeliculasByGeneroId(Long generoId) throws GeneroNotFoundException;

    void deletePeliculaById(Long peliculaId) throws Exception;

    DetallePeliculaResponseDto updatePelicula(Long peliculaId, UpdatePeliculaRequestDto peliculaRequest) throws PeliculaNotFoundException, PersonajeNotFoundException, PeliculaAlreadyExistsException;

    DetallePeliculaResponseDto agregarPersonajeToPelicula(Long idMovie, Long idCharacter) throws PeliculaNotFoundException, PersonajeNotFoundException, PersonajeYaEnUsoException;

    DetallePeliculaResponseDto eliminarPersonajeDePelicula(Long idMovie, Long idCharacter) throws PersonajeNotFoundException, PeliculaNotFoundException, PersonajeNotFoundInPeliculaException;
}
