package com.challenge.alkemy.controller;

import com.challenge.alkemy.entity.dto.peliculaDto.response.PeliculaConDetalleResponseDto;
import com.challenge.alkemy.entity.dto.personajeDto.request.CreateOrUpdatePersonajeRequestDto;
import com.challenge.alkemy.entity.dto.personajeDto.response.PersonajeBuscadoPorParametroResponseDto;
import com.challenge.alkemy.entity.dto.personajeDto.response.PersonajeConDetalleResponseDto;
import com.challenge.alkemy.error.personaje.PersonajeNotFoundException;
import com.challenge.alkemy.error.personaje.PersonajeYaEnUsoException;
import com.challenge.alkemy.service.PersonajeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.NoSuchElementException;

@RestController
@AllArgsConstructor
public class PersonajeController {

    private final PersonajeService personajeService;

    @Operation(summary = "Obtener todos los personajes")
    @GetMapping("/personaje")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Personajes",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PersonajeConDetalleResponseDto.class)))}),
            @ApiResponse(responseCode = "403", description = "User not authenticated",
                    content = @Content)})
    public ResponseEntity getAllPersonajes() {

        try {
            return ResponseEntity.ok(personajeService.getAllPersonajes());
        } catch (Exception e) {
            return new ResponseEntity("ALGO SALIO MAL", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Obtener un personaje por Id")
    @GetMapping("/personaje/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Personaje",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PersonajeConDetalleResponseDto.class)))}),
            @ApiResponse(responseCode = "403", description = "User not authenticated",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Personaje not found",
                    content = @Content)})
    public ResponseEntity getPersonajeById(@PathVariable("id") Long personajeId) {

        try {
            return ResponseEntity.ok(personajeService.getPersonajeById(personajeId));
        } catch (PersonajeNotFoundException personajeNotFoundException) {
            return new ResponseEntity("NO SE ENCONTRO PERSONAJE CON EL ID INDICADO", HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            return new ResponseEntity("ALGO SALIO MAL", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Crear un personaje")
    @PostMapping("/personaje")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Personaje created",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PersonajeConDetalleResponseDto.class)))}),
            @ApiResponse(responseCode = "403", description = "User not authenticated",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Personaje already exists",
                    content = @Content)})
    public ResponseEntity createPersonaje(@Valid @RequestBody CreateOrUpdatePersonajeRequestDto personajeRequest) {

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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Personaje Removed",
                    content = {@Content}),
            @ApiResponse(responseCode = "403", description = "User not authenticated",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Personaje Not Found",
                    content = @Content)})
    public ResponseEntity deletePersonajeById(@PathVariable("id") Long personajeId) {

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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Personaje updated",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PersonajeConDetalleResponseDto.class)))}),
            @ApiResponse(responseCode = "403", description = "User not authenticated",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Personaje not found",
                    content = @Content)})
    public ResponseEntity updatePersonaje(@PathVariable("id") Long personajeId,@Valid @RequestBody CreateOrUpdatePersonajeRequestDto personajeRequest) {
        try {
            return ResponseEntity.ok(personajeService.updatePersonaje(personajeId, personajeRequest));
        } catch (PersonajeNotFoundException personajeNotFoundException) {
            return new ResponseEntity("NO SE ENCONTRO PERSONAJE A EDITAR CON ESE ID", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity("ALGO SALIO MAL", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Busqueda de personajes con parametros")
    @GetMapping("/characters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Personaje buscado con parametro",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PersonajeBuscadoPorParametroResponseDto.class)))}),
            @ApiResponse(responseCode = "403", description = "User not authenticated",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content)})
    public ResponseEntity getPersonajesWithParameters(
            @RequestParam(required = false, name = "nombre") String nombre,
            @RequestParam(required = false, name = "edad") Integer edad,
            @RequestParam(required = false, name = "idMovie") Long idMovie,
            @RequestParam(required = false, name = "peso") Double peso
            ) {

        if (nombre != null) {
            try {
                return ResponseEntity.ok(personajeService.getPersonajeByNombre(nombre));
            } catch (PersonajeNotFoundException personajeNotFoundException) {
                return new ResponseEntity("NO SE ENCONTRO NINGUN PERSONAJE CON EL NOMBRE INGRESADO", HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                return new ResponseEntity("ALGO SALIO MAL", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        if (edad != null) {
            try {
                return ResponseEntity.ok(personajeService.getPersonajeByEdad(edad));
            } catch (PersonajeNotFoundException personajeNotFoundException) {
                return new ResponseEntity("NO SE ENCONTRO NINGUN PERSONAJE CON LA EDAD INGRESADA", HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                return new ResponseEntity("ALGO SALIO MAL", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        if (peso != null) {
            try {
                return ResponseEntity.ok(personajeService.getPersonajeByPeso(peso));
            } catch (PersonajeNotFoundException personajeNotFoundException) {
                return new ResponseEntity("NO SE ENCONTRO NINGUN PERSONAJE CON EL PESO INGRESADO", HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                return new ResponseEntity("ALGO SALIO MAL", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        if (idMovie != null) {

            try {
                return ResponseEntity.ok(personajeService.getPersonajesByPeliculaId(idMovie));
            } catch (NoSuchElementException noSuchElementException) {
                return new ResponseEntity("NO SE ENCONTRO PELICULA CON ESE ID", HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                return new ResponseEntity("ALGO SALIO MAL", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        // Si no se ingreso ningun parametro devolvemos la lista de personajes completa.
        try {
            return ResponseEntity.ok(personajeService.getPersonajes());
        } catch (Exception e) {
            return new ResponseEntity("ALGO SALIO MAL", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}
