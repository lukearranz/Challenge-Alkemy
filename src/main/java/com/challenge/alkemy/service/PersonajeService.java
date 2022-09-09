package com.challenge.alkemy.service;

import com.challenge.alkemy.dto.PersonajeResponseDto;
import com.challenge.alkemy.entity.Personaje;
import com.challenge.alkemy.error.PersonajeNotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface PersonajeService {

    List<Personaje> fetchPersonajes();

    Personaje savePersonaje(Personaje personaje);

    Personaje fetchPersonajeById(Long personajeId) throws PersonajeNotFoundException;

    void deletePersonajeById(Long personajeId) throws PersonajeNotFoundException;

    Personaje updatePersonaje(Long personajeId, Personaje personaje);

    List<PersonajeResponseDto> fetchPersonajeByNombre(String nombre);

    List<PersonajeResponseDto> fetchPersonajeByEdad(int edad);

    Object fetchPersonajeByPelicula(Long pelicula);

    List<PersonajeResponseDto> fetchPersonajeByPeso(Double peso);

    List<PersonajeResponseDto> fetchCharacters();
}
