package com.challenge.alkemy.service;

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

    List<Personaje> fetchPersonajeByNombre(String nombre);

    List<Personaje> fetchPersonajeByEdad(int edad);

    Object fetchPersonajeByPelicula(Long pelicula);

    List<Personaje> fetchPersonajeByPeso(Double peso);
}
