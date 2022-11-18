package com.challenge.alkemy.controller;

import com.challenge.alkemy.entity.dto.generoDto.response.GeneroResponseDto;
import com.challenge.alkemy.entity.dto.peliculaDto.request.CreatePeliculaRequestDto;
import com.challenge.alkemy.entity.dto.peliculaDto.request.UpdatePeliculaRequestDto;
import com.challenge.alkemy.entity.dto.peliculaDto.response.PeliculaBuscadaPorParametroResponseDto;
import com.challenge.alkemy.entity.dto.peliculaDto.response.PeliculaConDetalleResponseDto;
import com.challenge.alkemy.error.genero.GeneroNotFoundException;
import com.challenge.alkemy.error.pelicula.PeliculaAlreadyExistsException;
import com.challenge.alkemy.error.pelicula.PeliculaBuscadaPorParametroIncorrectoException;
import com.challenge.alkemy.error.pelicula.PeliculaNotFoundException;
import com.challenge.alkemy.error.personaje.PersonajeNotFoundException;
import com.challenge.alkemy.error.personaje.PersonajeNotFoundInPeliculaException;
import com.challenge.alkemy.error.personaje.PersonajeYaEnUsoException;
import com.challenge.alkemy.service.GeneroService;
import com.challenge.alkemy.service.PeliculaService;
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
public class PeliculaController {

    private final PeliculaService peliculaService;
    private final GeneroService generoService;

    @Operation(summary = "Obtener todas las peliculas")
    @GetMapping("/pelicula")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Peliculas",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PeliculaConDetalleResponseDto.class)))}),
            @ApiResponse(responseCode = "403", description = "User not authenticated",
                    content = @Content)
    })
    public ResponseEntity getAllPeliculas() {

        try {
            return ResponseEntity.ok(peliculaService.getAllPeliculas());
        } catch (Exception e) {
            return new ResponseEntity("ALGO SALIO MAL", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Obtener una pelicula por Id")
    @GetMapping("/pelicula/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Pelicula",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PeliculaConDetalleResponseDto.class)))}),
            @ApiResponse(responseCode = "403", description = "User not authenticated",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Pelicula not found",
                    content = @Content)
    })
    public ResponseEntity getPeliculaById(@PathVariable("id") Long peliculaId) {

        try {
            return ResponseEntity.ok(peliculaService.getPeliculaById(peliculaId));
        } catch (PeliculaNotFoundException peliculaNotFoundException) {
            return new ResponseEntity<>("NO SE ENCONTRO PELICULA CON ESE ID", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("ALGO SALIO MAL", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Crear nueva pelicula")
    @PostMapping("/pelicula")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pelicula created",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PeliculaConDetalleResponseDto.class)))}),
            @ApiResponse(responseCode = "403", description = "User not authenticated",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Personaje not found",
                    content = @Content),
            @ApiResponse(responseCode = "405", description = "Genero not found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Pelicula already exists",
                    content = @Content)
    })
    public ResponseEntity createPelicula(@Valid @RequestBody CreatePeliculaRequestDto request) throws PersonajeNotFoundException, PeliculaAlreadyExistsException, GeneroNotFoundException {

        try {
            return  ResponseEntity.ok(peliculaService.createPelicula(request));
        } catch (PeliculaAlreadyExistsException peliculaAlreadyExistsException) {
            return new ResponseEntity("LA PELICULA QUE QUIERES GUARDAR YA EXISTE", HttpStatus.BAD_REQUEST);
        } catch (PersonajeNotFoundException personajeNotFoundException) {
            return new ResponseEntity("NO SE ENCONTRARON PERSONAJES CON ESE ID", HttpStatus.NOT_FOUND);
        } catch (NoSuchElementException noSuchElementException) {
            return new ResponseEntity<>("NO SE ENCONTRO GENERO CON ESE ID", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("ALGO SALIO MAL", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Eliminar una Pelicula por Id")
    @DeleteMapping("/pelicula/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pelicula Removed",
                    content = {@Content}),
            @ApiResponse(responseCode = "403", description = "User not authenticated",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Pelicula Not Found",
                    content = @Content)})
    public ResponseEntity deletePeliculaById(@PathVariable("id") Long peliculaId) {

        try {
            peliculaService.deletePeliculaById(peliculaId);
            return new  ResponseEntity<>("PELICULA ELIMINADA CON EXITO", HttpStatus.OK);
        } catch (PeliculaNotFoundException peliculaNotFoundException) {
            return new ResponseEntity<>("NO SE ENCONTRO NINGUNA PELICULA CON ESE ID", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("ALGO SALIO MAL", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Editar una Pelicula por id")
    @PutMapping("/pelicula/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pelicula updated",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PeliculaConDetalleResponseDto.class)))}),
            @ApiResponse(responseCode = "403", description = "User not authenticated",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "One of the parameters not found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Pelicula already exists",
                    content = @Content)})
    public ResponseEntity updatePelicula(@Valid @PathVariable("id") Long peliculaId, @RequestBody UpdatePeliculaRequestDto peliculaRequest) {

        try {
            return ResponseEntity.ok(peliculaService.updatePelicula(peliculaId, peliculaRequest));
        } catch (PeliculaNotFoundException peliculaNotFoundException) {
            return new ResponseEntity<>("NO SE ENCONTRO PELICULA CON ESE ID", HttpStatus.NOT_FOUND);
        } catch (PersonajeNotFoundException personajeNotFoundException) {
            return new ResponseEntity<>("NO SE ENCONTRO PERSONAJE CON ESE ID", HttpStatus.NOT_FOUND);
        } catch (NoSuchElementException noSuchElementException) {
            return new ResponseEntity<>("NO SE ENCONTRO GENERO CON ESE ID", HttpStatus.NOT_FOUND);
        } catch (PeliculaAlreadyExistsException peliculaAlreadyExistsException) {
            return new ResponseEntity<>("EL TITULO SOLICITADO YA ESTA EN USO", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("ALGO SALIO MAL", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Busqueda de peliculas con parametros")
    @GetMapping("/movies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Pelicula",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PeliculaBuscadaPorParametroResponseDto.class)))}),
            @ApiResponse(responseCode = "403", description = "User not authenticated",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Pelicula not found",
                    content = @Content)})
    public ResponseEntity getMoviesWithParameters(@Valid @RequestParam(required = false, name = "nombre") String nombre, @RequestParam(required = false, name = "genero") Long idGenero, @RequestParam(required = false, name = "orden") String orden
    ) throws PeliculaNotFoundException {

        if (nombre != null) {
            try {
                return ResponseEntity.ok(peliculaService.getPeliculaByTitulo(nombre));
            } catch (PeliculaNotFoundException peliculaNotFoundException) {
                return new ResponseEntity("NO SE ENCONTRO NINGUNA PELICULA CON EL TITULO INGRESADO", HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                return new ResponseEntity("ALGO SALIO MAL", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        if (orden != null) {
            try {
                return ResponseEntity.ok(peliculaService.getPeliculasByOrder(orden));
            } catch (PeliculaNotFoundException peliculaNotFoundException) {
                return new ResponseEntity("NO SE ENCONTRARON PELICULAS A ORDENAR", HttpStatus.NOT_FOUND);
            } catch (PeliculaBuscadaPorParametroIncorrectoException peliculaBuscadaPorParametroIncorrectoException) {
                return new ResponseEntity("EL PARAMETRO DE ORDENAMIENTO INGRESADO ES INCORRECTO", HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                return new ResponseEntity("ALGO SALIO MAL",HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        if (idGenero != null) {
            try {
                return ResponseEntity.ok(peliculaService.getPeliculasByGeneroId(idGenero));
            } catch (GeneroNotFoundException generoNotFoundException) {
                return new ResponseEntity("NO SE ENCONTRO GENERO CON ESE ID", HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                return new ResponseEntity("ALGO SALIO MAL", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        // Si no se ingreso ningun parametro devolvemos la lista de peliculas completa
        try {
            return ResponseEntity.ok(peliculaService.getPeliculasSinParametros());
        } catch (PeliculaNotFoundException peliculaNotFoundException) {
            return new ResponseEntity("NO SE ENCONTRARON PELICULAS EN LA DB", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity("ALGO SALIO MAL", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Agregar personaje a una pelicula por Id")
    @PostMapping("/movies/{idMovie}/characters/{idCharacter}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Personaje added to Pelicula",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PeliculaConDetalleResponseDto.class)))}),
            @ApiResponse(responseCode = "403", description = "User not authenticated",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Pelicula not found",
                    content = @Content)})
    ResponseEntity addPersonajeToPelicula(
            @PathVariable Long idMovie,
            @PathVariable Long idCharacter
    ) {
        try {
            return ResponseEntity.ok(peliculaService.addPersonajeToPelicula(idMovie, idCharacter));
        } catch (PersonajeNotFoundException personajeNotFoundException) {
            return new ResponseEntity<>("NO SE ENCONTRO UN PERSONJE CON ESE ID", HttpStatus.NOT_FOUND);
        } catch (PeliculaNotFoundException peliculaNotFoundException) {
            return new ResponseEntity<>("NO SE ENCONTRO PELICULA CON ESE ID", HttpStatus.NOT_FOUND);
        } catch (PersonajeYaEnUsoException personajeYaEnUsoException) {
            return new ResponseEntity<>("EL PERSONAJE QUE DESEA AGREGAR YA ESTA EN LA PELICULA", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("ALGO SALIO MAL", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Eliminar personaje de una pelicula por Id")
    @DeleteMapping("/movies/{idMovie}/characters/{idCharacter}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Personaje deleted of Pelicula",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PeliculaConDetalleResponseDto.class)))}),
            @ApiResponse(responseCode = "403", description = "User not authenticated",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Pelicula not found",
                    content = @Content)})
    ResponseEntity deletePersonajeDePelicula(
            @PathVariable Long idMovie,
            @PathVariable Long idCharacter
    ) {
        try {
            return ResponseEntity.ok(peliculaService.deletePersonajeDePelicula(idMovie, idCharacter));
        } catch (PeliculaNotFoundException peliculaNotFoundException) {
            return new ResponseEntity<>("NO SE ENCONTRO PELICULA CON ESE ID" ,HttpStatus.NOT_FOUND);
        } catch (PersonajeNotFoundException personajeNotFoundException) {
            return new ResponseEntity<>("NO SE ENCONTRO PERSONAJE CON ESE ID" ,HttpStatus.NOT_FOUND);
        } catch (PersonajeNotFoundInPeliculaException personajeNotFoundInPeliculaException) {
            return new ResponseEntity<>("NO SE ENCONTRO EL PERSONAJE EN LA PELICULA INDICADA" ,HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("ALGO SALIO MAL" ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
