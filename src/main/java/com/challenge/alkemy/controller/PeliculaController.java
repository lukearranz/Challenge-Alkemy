package com.challenge.alkemy.controller;

import com.challenge.alkemy.entity.Genero;
import com.challenge.alkemy.entity.dto.peliculaDto.request.CreatePeliculaRequestDto;
import com.challenge.alkemy.entity.dto.peliculaDto.response.PeliculaBuscadaPorParametroResponseDto;
import com.challenge.alkemy.entity.Pelicula;
import com.challenge.alkemy.error.ChallengeAlkemyException;
import com.challenge.alkemy.error.genero.GeneroNotFoundException;
import com.challenge.alkemy.error.pelicula.PeliculaAlreadyExistsException;
import com.challenge.alkemy.error.pelicula.PeliculaNotFound;
import com.challenge.alkemy.error.personaje.PersonajeNotFoundException;
import com.challenge.alkemy.service.GeneroService;
import com.challenge.alkemy.service.PeliculaService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class PeliculaController {

    @Autowired
    private PeliculaService peliculaService;

    @Autowired
    private GeneroService generoService;

    // Logger for debugging the application
    private final Logger LOGGER = LoggerFactory.getLogger(PersonajeController.class);

    @Operation(summary = "Obtener una pelicula por Id")
    @GetMapping("/pelicula/{id}")
    public ResponseEntity<Object> fetchPeliculaById(@PathVariable("id") Long peliculaId) {
        LOGGER.info("INSIDE FETCH_PELICULA_BY_ID_PELICULA -----> PELICULA_CONTROLLER");

        try {
            Optional pelicula = peliculaService.fetchPeliculaById(peliculaId);
            if (pelicula.isPresent()) {
                return ResponseEntity.ok(pelicula);
            }
            return new ResponseEntity<>("No se encontro pelicula con ese Id", HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>("No se encontro pelicula con ese Id", HttpStatus.NOT_FOUND);
        }


    }

    @Operation(summary = "Obtener todas las peliculas")
    @GetMapping("/pelicula")
    public ResponseEntity<Object> fetchPeliculas() {

        LOGGER.info("INSIDE FETCH_PELICULAS -----> PELICULA_CONTROLLER");
        return peliculaService.fetchAllPeliculas();


    }

    @Operation(summary = "Crear nueva pelicula")
    @PostMapping("/pelicula")
    public ResponseEntity createPelicula(@Valid @RequestBody CreatePeliculaRequestDto request) throws ChallengeAlkemyException, PersonajeNotFoundException, PeliculaAlreadyExistsException, GeneroNotFoundException {
        LOGGER.info("INSIDE SAVE_PELICULA -----> PELICULA_CONTROLLER");
        return  ResponseEntity.ok(peliculaService.createPelicula(request));
    }

    @Operation(summary = "Eliminar una Pelicula por Id")
    @DeleteMapping("/pelicula/{id}")
    public ResponseEntity<Object> deletePeliculaById(@PathVariable("id") Long peliculaId) {
        LOGGER.info("INSIDE DELETE_PELICULA -----> PELICULA_CONTROLLER");
        try {
            peliculaService.deletePeliculaById(peliculaId);
        } catch (Exception e) {
            return new ResponseEntity<>("No se encontro ninguna pelicula con ese ID", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok("Pelicula eliminada con exito");
    }

    @Operation(summary = "Editar una Pelicula por Id")
    @PutMapping("/pelicula/{id}")
    public ResponseEntity<Object> updatePelicula(@PathVariable("id") Long peliculaId, @RequestBody Pelicula pelicula) {
        try {
            return ResponseEntity.ok(peliculaService.updatePelicula(peliculaId, pelicula));
        } catch (Exception e) {
            return new ResponseEntity<>("No se encontro pelicula a Editar con ese Id", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Busqueda de peliculas con parametros")
    @GetMapping("/movies")
    public ResponseEntity fetchMoviesWithParameters(@Valid
            @RequestParam(required = false, name = "nombre") String nombre,
            @RequestParam(required = false, name = "genero") Long idGenero,
            @RequestParam(required = false, name = "orden") String orden
    ) throws PeliculaNotFound {

        LOGGER.info("INSIDE FETCH_MOVIES -----> PELICULAS_CONTROLLER");

        if (nombre != null) {
            PeliculaBuscadaPorParametroResponseDto pelicula = peliculaService.fetchPeliculaByTitulo(nombre);

            if (pelicula.getTitulo().isEmpty()) {
                return new ResponseEntity<>("No se encontraron peliculas con el nombre ingresado", HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(pelicula);
        }

        if (orden != null) {
            List<PeliculaBuscadaPorParametroResponseDto> peliculasOrdenadas = peliculaService.fetchPeliculasByOrder(orden);
            if (peliculasOrdenadas == null || peliculasOrdenadas.isEmpty()) {
                return new ResponseEntity<>("No se encontraron peliculas, verificar parametros de la query", HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(peliculasOrdenadas);
        }

        if (idGenero != null) {

            try {
                List<PeliculaBuscadaPorParametroResponseDto> peliculasDB = peliculaService.fetchPeliculasByGeneroId(idGenero);
                return ResponseEntity.ok(peliculasDB);
            } catch (Exception e) {
                return new ResponseEntity<>("No se encontro Genero con ese ID", HttpStatus.NOT_FOUND);
            }
        }

        List<PeliculaBuscadaPorParametroResponseDto> peliculas = peliculaService.fetchPeliculasSinParametros();
        if (peliculas.isEmpty()) {
            return new ResponseEntity<>("No se encontraron peliculas", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(peliculas);

    }

    @Operation(summary = "Agregar personaje a una pelicula por Id")
    @PostMapping("/movies/{idMovie}/characters/{idCharacter}")
    ResponseEntity<Object> addCharacterToMovie(
            @PathVariable Long idMovie,
            @PathVariable Long idCharacter
    ) {
        try {
            return peliculaService.agregarPersonajeToPelicula(idMovie, idCharacter);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Eliminar personaje de una pelicula por Id")
    @DeleteMapping("/movies/{idMovie}/characters/{idCharacter}")
    ResponseEntity<Object> deleteCharacterOfMovie(
            @PathVariable Long idMovie,
            @PathVariable Long idCharacter
    ) {
        try {
            return peliculaService.eliminarPersonajeDePelicula(idMovie, idCharacter);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
