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

    PeliculaConDetalleResponseDto fetchPeliculaById(Long peliculaId) throws PeliculaNotFoundException;

    PeliculaBuscadaPorParametroResponseDto fetchPeliculaByTitulo(String titulo) throws PeliculaNotFoundException;

    List<PeliculaBuscadaPorParametroResponseDto> fetchPeliculasSinParametros() throws PeliculaNotFoundException;

    List<PeliculaConDetalleResponseDto> fetchAllPeliculas() throws PeliculaNotFoundException;

    List<PeliculaBuscadaPorParametroResponseDto> fetchPeliculasByOrder(String orden) throws PeliculaNotFoundException, PeliculaBuscadaPorParametroIncorrectoException;

    List<PeliculaBuscadaPorParametroResponseDto> fetchPeliculasByGeneroId(Long generoId) throws GeneroNotFoundException;

    void deletePeliculaById(Long peliculaId) throws Exception;

    PeliculaConDetalleResponseDto updatePelicula(Long peliculaId, UpdatePeliculaRequestDto peliculaRequest) throws PeliculaNotFoundException, PersonajeNotFoundException, PeliculaAlreadyExistsException;

    PeliculaConDetalleResponseDto agregarPersonajeToPelicula(Long idMovie, Long idCharacter) throws PeliculaNotFoundException, PersonajeNotFoundException, PersonajeYaEnUsoException;

    PeliculaConDetalleResponseDto eliminarPersonajeDePelicula(Long idMovie, Long idCharacter) throws PersonajeNotFoundException, PeliculaNotFoundException, PersonajeNotFoundInPeliculaException;
}
