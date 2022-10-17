package com.challenge.alkemy.controller;

import com.challenge.alkemy.entity.Personaje;
import com.challenge.alkemy.entity.dto.personajeDto.request.CreateOrUpdatePersonajeRequestDto;
import com.challenge.alkemy.error.personaje.PersonajeNotFoundException;
import com.challenge.alkemy.error.personaje.PersonajeYaEnUsoException;
import com.challenge.alkemy.service.PersonajeService;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.NoSuchElementException;

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

    @Operation(summary = "Obtener todos los personajes")
    @GetMapping("/personaje")
    public ResponseEntity fetchPersonajes() {
        try {
            return ResponseEntity.ok(personajeService.fetchPersonajes());
        } catch (Exception e) {
            return new ResponseEntity("ALGO SALIO MAL", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Obtener un personaje por Id")
    @GetMapping("/personaje/{id}")
    public ResponseEntity fetchPersonajeById(@ApiParam("Clave Primaria tipo Long") @PathVariable("id") Long personajeId) {
        LOGGER.info("INSIDE FETCH_PERSONAJE_BY_ID -----> PERSONAJE_CONTROLLER");
        try {
            return ResponseEntity.ok(personajeService.fetchPersonajeById(personajeId));
        } catch (PersonajeNotFoundException personajeNotFoundException) {
            return new ResponseEntity("NO SE ENCONTRO PERSONAJE CON EL ID INDICADO", HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            return new ResponseEntity("ALGO SALIO MAL", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Crear un personaje")
    @PostMapping("/personaje")
    public ResponseEntity createPersonaje(@Valid @RequestBody CreateOrUpdatePersonajeRequestDto personajeRequest) {
        LOGGER.info("INSIDE SAVE_PERSONAJE -----> PERSONAJE_CONTROLLER");
        try {
            return ResponseEntity.ok(personajeService.savePersonaje(personajeRequest));
        } catch (PersonajeYaEnUsoException personajeYaEnUsoException) {
            return new ResponseEntity("EL PERSONAJE QUE DESEA CREAR YA EXISTE", HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            return new ResponseEntity("ALGO SALIO MAL", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Eliminar un personaje por Id")
    @DeleteMapping("/personaje/{id}")
    public ResponseEntity deletePersonajeById(@PathVariable("id") Long personajeId) {
        LOGGER.info("INSIDE DELETE_PERSONAJE -----> PERSONAJE_CONTROLLER");
        try {
            personajeService.deletePersonajeById(personajeId);
            return ResponseEntity.ok("PERSONAJE ELIMINADO CON EXITO");
        } catch (PersonajeNotFoundException personajeNotFoundException) {
            return new ResponseEntity("NO SE ENCONTRO PERSONAJE CON ESE ID", HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            return new ResponseEntity("ALGO SALIO MAL", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Editar un personaje")
    @PutMapping("/personaje/{id}")
    public ResponseEntity updatePersonaje(@PathVariable("id") Long personajeId,@Valid @RequestBody CreateOrUpdatePersonajeRequestDto personajeRequest) {
        try {
            return ResponseEntity.ok(personajeService.updatePersonaje(personajeId, personajeRequest));
        } catch (PersonajeNotFoundException personajeNotFoundException) {
            return new ResponseEntity("NO SE ENCONTRO PERSONAJE A EDITAR CON ESE ID", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity("ALGO SALIO MAL", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
