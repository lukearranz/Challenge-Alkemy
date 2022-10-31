package com.challenge.alkemy.service;

import com.challenge.alkemy.entity.dto.peliculaDto.request.CreatePeliculaRequestDto;
import com.challenge.alkemy.entity.dto.peliculaDto.request.UpdatePeliculaRequestDto;
import com.challenge.alkemy.entity.dto.peliculaDto.response.PeliculaConDetalleResponseDto;
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
    PeliculaConDetalleResponseDto createPelicula(CreatePeliculaRequestDto pelicula) throws PersonajeNotFoundException, PeliculaAlreadyExistsException, GeneroNotFoundException;

    PeliculaConDetalleResponseDto getPeliculaById(Long peliculaId) throws PeliculaNotFoundException;

    PeliculaBuscadaPorParametroResponseDto getPeliculaByTitulo(String titulo) throws PeliculaNotFoundException;

    List<PeliculaBuscadaPorParametroResponseDto> getPeliculasSinParametros() throws PeliculaNotFoundException;

    List<PeliculaConDetalleResponseDto> getAllPeliculas() throws PeliculaNotFoundException;

    List<PeliculaBuscadaPorParametroResponseDto> getPeliculasByOrder(String orden) throws PeliculaNotFoundException, PeliculaBuscadaPorParametroIncorrectoException;

    List<PeliculaBuscadaPorParametroResponseDto> getPeliculasByGeneroId(Long generoId) throws GeneroNotFoundException;

    void deletePeliculaById(Long peliculaId) throws Exception;

    PeliculaConDetalleResponseDto updatePelicula(Long peliculaId, UpdatePeliculaRequestDto peliculaRequest) throws PeliculaNotFoundException, PersonajeNotFoundException, PeliculaAlreadyExistsException;

    PeliculaConDetalleResponseDto addPersonajeToPelicula(Long idMovie, Long idCharacter) throws PeliculaNotFoundException, PersonajeNotFoundException, PersonajeYaEnUsoException;

    PeliculaConDetalleResponseDto deletePersonajeDePelicula(Long idMovie, Long idCharacter) throws PersonajeNotFoundException, PeliculaNotFoundException, PersonajeNotFoundInPeliculaException;
}
