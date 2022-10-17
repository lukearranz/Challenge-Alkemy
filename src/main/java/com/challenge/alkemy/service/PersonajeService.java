package com.challenge.alkemy.service;

import com.challenge.alkemy.entity.dto.personajeDto.request.CreateOrUpdatePersonajeRequestDto;
import com.challenge.alkemy.entity.dto.personajeDto.response.PersonajeBuscadoPorParametroResponseDto;
import com.challenge.alkemy.entity.dto.personajeDto.response.PersonajeConDetalleResponseDto;
import com.challenge.alkemy.error.personaje.PersonajeNotFoundException;
import com.challenge.alkemy.error.personaje.PersonajeYaEnUsoException;


import java.util.List;


public interface PersonajeService {

    List<PersonajeConDetalleResponseDto> fetchPersonajes();

    PersonajeConDetalleResponseDto savePersonaje(CreateOrUpdatePersonajeRequestDto personaje) throws PersonajeYaEnUsoException;

    PersonajeConDetalleResponseDto fetchPersonajeById(Long personajeId) throws PersonajeNotFoundException;

    void deletePersonajeById(Long personajeId) throws Exception;

    PersonajeConDetalleResponseDto updatePersonaje(Long personajeId, CreateOrUpdatePersonajeRequestDto personajeRequest) throws PersonajeNotFoundException;

    PersonajeBuscadoPorParametroResponseDto fetchPersonajeByNombre(String nombre) throws PersonajeNotFoundException;

    List<PersonajeBuscadoPorParametroResponseDto> fetchPersonajeByEdad(int edad) throws PersonajeNotFoundException;

    List<PersonajeBuscadoPorParametroResponseDto> fetchPersonajeByPeso(Double peso) throws PersonajeNotFoundException;

    List<PersonajeBuscadoPorParametroResponseDto> fetchCharacters();

    List<PersonajeBuscadoPorParametroResponseDto> fetchPersonajesByPeliculaId(Long idMovie);
}
