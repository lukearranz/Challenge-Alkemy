package com.challenge.alkemy.controller;

import com.challenge.alkemy.entity.dto.personajeDto.response.PersonajeResponseDto;
import com.challenge.alkemy.entity.Personaje;
import com.challenge.alkemy.error.personaje.PersonajeNotFoundException;
import com.challenge.alkemy.service.PersonajeService;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class PersonajeController {

    private PersonajeService personajeService;

    // Logger for debugging the application
    private final Logger LOGGER = LoggerFactory.getLogger(PersonajeController.class);

    @Operation(summary = "Busqueda de personajes con parametros")
    @GetMapping("/characters")
    public ResponseEntity fetchPersonajesWithParameters(
            @RequestParam(required = false, name = "nombre") String nombre,
            @RequestParam(required = false, name = "edad") Integer edad,
            @RequestParam(required = false, name = "idMovie") Long idMovie,
            @RequestParam(required = false, name = "peso") Double peso
            ) {
        LOGGER.info("INSIDE FETCH_PERSONAJES -----> PERSONAJE_CONTROLLER");

        if (nombre != null) {
            try {
                return ResponseEntity.ok(personajeService.fetchPersonajeByNombre(nombre));
            } catch (PersonajeNotFoundException personajeNotFoundException) {
                return new ResponseEntity("NO SE ENCONTRO NINGUN PERSONAJE CON EL NOMBRE INGRESADO", HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                return new ResponseEntity("ALGO SALIO MAL", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        if (edad != null) {
            try {
                return ResponseEntity.ok(personajeService.fetchPersonajeByEdad(edad));
            } catch (PersonajeNotFoundException personajeNotFoundException) {
                return new ResponseEntity("NO SE ENCONTRO NINGUN PERSONAJE CON LA EDAD INGRESADA", HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                return new ResponseEntity("ALGO SALIO MAL", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        if (peso != null) {
            try {
                return ResponseEntity.ok(personajeService.fetchPersonajeByPeso(peso));
            } catch (PersonajeNotFoundException personajeNotFoundException) {
                return new ResponseEntity("NO SE ENCONTRO NINGUN PERSONAJE CON EL PESO INGRESADO", HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                return new ResponseEntity("ALGO SALIO MAL", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        if (idMovie != null) {
            LOGGER.info("INSIDE FETCH_PERSONAJES_BY_ID_MOVIE (" + idMovie + ") -----> PERSONAJE_CONTROLLER");
            try {
                return ResponseEntity.ok(personajeService.fetchPersonajesByPeliculaId(idMovie));
            } catch (NoSuchElementException noSuchElementException) {
                return new ResponseEntity("NO SE ENCONTRO PELICULA CON ESE ID", HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                return new ResponseEntity("ALGO SALIO MAL", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        // Si no se ingreso ningun parametro devolvemos la lista de personajes completa.
        try {
            return ResponseEntity.ok(personajeService.fetchCharacters());
        } catch (Exception e) {
            return new ResponseEntity("ALGO SALIO MAL", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*


    
    WORKING HERE



     */

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

    @Operation(summary = "Eliminar un personaje por Id")
    @DeleteMapping("/personaje/{id}")
    public ResponseEntity<String> deletePersonajeById(@PathVariable("id") Long personajeId) {
        LOGGER.info("INSIDE DELETE_PERSONAJE -----> PERSONAJE_CONTROLLER");
        try {
            personajeService.deletePersonajeById(personajeId);
        } catch (Exception e) {
            return new ResponseEntity<>("No se pudo eliminar el personaje", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Personaje eliminado con exito", HttpStatus.OK);
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
