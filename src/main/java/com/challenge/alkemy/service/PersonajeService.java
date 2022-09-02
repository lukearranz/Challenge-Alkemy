package com.challenge.alkemy.service;

import com.challenge.alkemy.entity.Personaje;
import com.challenge.alkemy.error.PersonajeNotFoundException;

import java.util.List;


public interface PersonajeService {

    Personaje savePersonaje(Personaje personaje);

    List<Personaje> fetchPersonajes();

    Personaje fetchPersonajeById(Long personajeId) throws PersonajeNotFoundException;

    void deletePersonajeById(Long personajeId) throws PersonajeNotFoundException;

    Personaje updatePersonaje(Long personajeId, Personaje personaje);
}
