package com.challenge.alkemy.service;

import com.challenge.alkemy.dto.PersonajeResponseDto;
import com.challenge.alkemy.entity.Personaje;


import java.util.List;
import java.util.Optional;


public interface PersonajeService {

    List<Personaje> fetchPersonajes();

    Personaje savePersonaje(Personaje personaje);

    Optional<Personaje> fetchPersonajeById(Long personajeId);

    void deletePersonajeById(Long personajeId);

    Personaje updatePersonaje(Long personajeId, Personaje personaje);

    List<PersonajeResponseDto> fetchPersonajeByNombre(String nombre);

    List<PersonajeResponseDto> fetchPersonajeByEdad(int edad);

    Object fetchPersonajeByPelicula(Long pelicula);

    List<PersonajeResponseDto> fetchPersonajeByPeso(Double peso);

    List<PersonajeResponseDto> fetchCharacters();

    List<PersonajeResponseDto> fetchPersonajesByPeliculaId(Long idMovie);
}
