package com.challenge.alkemy.service;

import com.challenge.alkemy.entity.dto.generoDto.request.CreateGeneroRequestDto;
import com.challenge.alkemy.entity.dto.generoDto.response.CreateGeneroResponseDto;
import com.challenge.alkemy.entity.dto.generoDto.response.GeneroResponseDto;
import com.challenge.alkemy.error.genero.GeneroAlreadyInUseException;
import com.challenge.alkemy.error.genero.GeneroNotFoundException;
import com.challenge.alkemy.error.pelicula.PeliculaNotFoundException;

import java.util.List;

public interface GeneroService {
    List<GeneroResponseDto> fetchGeneros() throws GeneroNotFoundException;

    CreateGeneroResponseDto saveGenero(CreateGeneroRequestDto genero) throws GeneroAlreadyInUseException, PeliculaNotFoundException;

    GeneroResponseDto findGeneroById(Long generoId) throws GeneroNotFoundException;

    void deleteGeneroById(Long generoId) throws GeneroNotFoundException;
}
