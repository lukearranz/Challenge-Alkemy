package com.challenge.alkemy.service;

import com.challenge.alkemy.entity.dto.personajeDto.response.PersonajeBuscadoPorParametroResponseDto;
import com.challenge.alkemy.entity.Personaje;
import com.challenge.alkemy.error.personaje.PersonajeNotFoundException;


import java.util.List;
import java.util.Optional;


public interface PersonajeService {

    List<Personaje> fetchPersonajes();

    Personaje savePersonaje(Personaje personaje);

    Optional<Personaje> fetchPersonajeById(Long personajeId);

    void deletePersonajeById(Long personajeId) throws Exception;

    Personaje updatePersonaje(Long personajeId, Personaje personaje);

    PersonajeBuscadoPorParametroResponseDto fetchPersonajeByNombre(String nombre) throws PersonajeNotFoundException;

    List<PersonajeBuscadoPorParametroResponseDto> fetchPersonajeByEdad(int edad) throws PersonajeNotFoundException;

    List<PersonajeBuscadoPorParametroResponseDto> fetchPersonajeByPeso(Double peso) throws PersonajeNotFoundException;

    List<PersonajeBuscadoPorParametroResponseDto> fetchCharacters();

    List<PersonajeBuscadoPorParametroResponseDto> fetchPersonajesByPeliculaId(Long idMovie);
}
