package com.challenge.alkemy.controller;

import com.challenge.alkemy.dto.PersonajeResponseDto;
import com.challenge.alkemy.entity.Personaje;
import com.challenge.alkemy.service.PersonajeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PersonajeController {

    @Autowired
    private PersonajeService personajeService;

    // Logger for debugging the application
    private final Logger LOGGER = LoggerFactory.getLogger(PersonajeController.class);

    @Operation(summary = "Busqueda de personajes con parametros")
    @GetMapping("/characters")
    public ResponseEntity<Object> fetchPersonajesWithParameters(
            @RequestParam(required = false, name = "nombre") String nombre,
            @RequestParam(required = false, name = "edad") Integer edad,
            @RequestParam(required = false, name = "peliculaId") Long peliculaId,
            @RequestParam(required = false, name = "peso") Double peso
            ) {
        LOGGER.info("INSIDE FETCH_PERSONAJES -----> PERSONAJE_CONTROLLER");

        if (nombre != null) {
            List<PersonajeResponseDto> personajes = personajeService.fetchPersonajeByNombre(nombre);
            if (personajes.isEmpty()) {
                return new ResponseEntity<>("No se encontraron personajes con el nombre ingresado", HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(personajes);
        }

        if (edad != null) {
            List<PersonajeResponseDto> personajes = personajeService.fetchPersonajeByEdad(edad);
            if (personajes.isEmpty()) {
                return new ResponseEntity<>("No se encontraron personajes con esa Edad", HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(personajes);
        }

        if (peso != null) {
            List<PersonajeResponseDto> personajes = personajeService.fetchPersonajeByPeso(peso);
            if (personajes.isEmpty()) {
                return new ResponseEntity<>("No se encontraron personajes con ese Peso", HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(personajes);
        }

        /*
        //ToDo id_pelicula
        if (peliculaId != null) {
            return ResponseEntity.ok(personajeService.fetchPersonajeByPelicula(peliculaId));
        }
        */


        List<PersonajeResponseDto> personajes = personajeService.fetchCharacters();
        if (personajes.isEmpty()) {
            return new ResponseEntity<>("No se encontraron personajes", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(personajes);
    }


    @Operation(summary = "Obtener todos los personajes")
    @GetMapping("/personaje")
    public ResponseEntity<Object> fetchPersonajes() {
        List<Personaje> personajes = personajeService.fetchPersonajes();
        if (personajes.isEmpty()) {
            return new ResponseEntity<>("No se encontraron personajes", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(personajes);
    }

    @Operation(summary = "Obtener un personaje por Id")
    @GetMapping("/personaje/{id}")
    public ResponseEntity<Object> fetchPersonajeById(@ApiParam("Clave primaria tipo Long") @PathVariable("id") Long personajeId) {
        LOGGER.info("INSIDE FETCH_PERSONAJE_BY_ID -----> PERSONAJE_CONTROLLER");
        try {
            Optional<Personaje> personaje = personajeService.fetchPersonajeById(personajeId);
            if (personaje.isEmpty()) {
                return new ResponseEntity<>("No se encontro personaje con ese Id", HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(personaje);
        } catch (Exception e) {
            return new ResponseEntity<>("No se encontro personaje con ese Id", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Crear un personaje")
    @PostMapping("/personaje")
    public Personaje savePersonaje(@RequestBody Personaje personaje) {
        LOGGER.info("INSIDE SAVE_PERSONAJE -----> PERSONAJE_CONTROLLER");
        return personajeService.savePersonaje(personaje);
    }

    @Operation(summary = "Eliminar un personaje")
    @DeleteMapping("/personaje/{id}")
    public ResponseEntity<String> deletePersonajeById(@PathVariable("id") Long personajeId) {
        LOGGER.info("INSIDE DELETE_PERSONAJE -----> PERSONAJE_CONTROLLER");
        try {
            personajeService.deletePersonajeById(personajeId);
        } catch (Exception e) {
            return new ResponseEntity<>("No se encontro personaje a Eliminar con ese Id", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Eliminado con exito", HttpStatus.OK);
    }

    @Operation(summary = "Editar un personaje")
    @PutMapping("/personaje/{id}")
    public ResponseEntity<Object> updatePersonaje(@PathVariable("id") Long personajeId, @RequestBody Personaje personaje) {
        try {
            return ResponseEntity.ok(personajeService.updatePersonaje(personajeId, personaje));
        } catch (Exception e) {
            return new ResponseEntity<>("No se encontro personaje a Editar con ese Id", HttpStatus.NOT_FOUND);
        }
    }

}
