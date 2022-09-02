package com.challenge.alkemy.controller;

import com.challenge.alkemy.entity.Personaje;
import com.challenge.alkemy.error.PersonajeNotFoundException;
import com.challenge.alkemy.service.PersonajeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonajeController {

    @Autowired
    private PersonajeService personajeService;

    // Logger for debugging the application
    private final Logger LOGGER = LoggerFactory.getLogger(PersonajeController.class);

    @GetMapping("/personaje")
    public ResponseEntity<Object> fetchPersonajes() {
        LOGGER.info("INSIDE FETCH_PERSONAJES -----> PERSONAJE_CONTROLLER");
        try {
            return ResponseEntity.ok(personajeService.fetchPersonajes());
        } catch (Exception e) {
            return new ResponseEntity<>("No se encontro personajes", HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/personaje/{id}")
    public ResponseEntity<Object> fetchPersonajeById(@PathVariable("id") Long personajeId) throws PersonajeNotFoundException {
        LOGGER.info("INSIDE FETCH_PERSONAJE_BY_ID -----> PERSONAJE_CONTROLLER");
        try {
            return ResponseEntity.ok(personajeService.fetchPersonajeById(personajeId)) ;
        } catch (Exception e) {
            return new ResponseEntity<>("No se encontro personaje con ese Id", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/personaje")
    public Personaje savePersonaje(@RequestBody Personaje personaje) {
        LOGGER.info("INSIDE SAVE_PERSONAJE -----> PERSONAJE_CONTROLLER");
        return personajeService.savePersonaje(personaje);
    }

    @DeleteMapping("/personaje/{id}")
    public ResponseEntity<String> deletePersonajeById(@PathVariable("id") Long personajeId) throws PersonajeNotFoundException {
        LOGGER.info("INSIDE DELETE_PERSONAJE -----> PERSONAJE_CONTROLLER");
        try {
            personajeService.deletePersonajeById(personajeId);
        } catch (Exception e) {
            return new ResponseEntity<>("Personaje not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Eliminado con exito", HttpStatus.OK);
    }

    @PutMapping("/personaje/{id}")
    public ResponseEntity<Object> updatePersonaje(@PathVariable("id") Long personajeId, @RequestBody Personaje personaje) {
        try {
            return ResponseEntity.ok(personajeService.updatePersonaje(personajeId, personaje));
        } catch (Exception e) {
            return new ResponseEntity<>("Personaje a editar not found", HttpStatus.NOT_FOUND);
        }
    }

}
