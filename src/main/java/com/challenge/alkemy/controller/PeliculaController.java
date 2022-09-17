package com.challenge.alkemy.controller;

import com.challenge.alkemy.dto.PeliculaResponseDto;
import com.challenge.alkemy.entity.Pelicula;
import com.challenge.alkemy.entity.Personaje;
import com.challenge.alkemy.service.PeliculaService;
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
public class PeliculaController {

    @Autowired
    private PeliculaService peliculaService;

    // Logger for debugging the application
    private final Logger LOGGER = LoggerFactory.getLogger(PersonajeController.class);

    @Operation(summary = "Obtener una pelicula por Id")
    @GetMapping("/pelicula/{id}")
    public ResponseEntity<Object> fetchPeliculaById(@PathVariable("id") Long peliculaId) {
        LOGGER.info("INSIDE FETCH_PELICULA_BY_ID_PELICULA -----> PELICULA_CONTROLLER");
        try {
            Optional<Pelicula> pelicula = peliculaService.fetchPeliculaById(peliculaId);
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
        List<Pelicula> peliculas = peliculaService.fetchAllPeliculas();
        if (peliculas.isEmpty()) {
            return new ResponseEntity<>("No se encontraron personajes", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(peliculas);

    }

    @Operation(summary = "Crear nueva pelicula")
    @PostMapping("/pelicula")
    public ResponseEntity<Object> savePelicula(@RequestBody Pelicula pelicula) {
        LOGGER.info("INSIDE SAVE_PELICULA -----> PELICULA_CONTROLLER");
        return  peliculaService.savePelicula(pelicula);
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

    @Operation(summary = "Busqueda de peliculas con parametros y DTO")
    @GetMapping("/movies")
    public ResponseEntity<Object> fetchMoviesWithParameters(
            @RequestParam(required = false, name = "nombre") String nombre,
            @RequestParam(required = false, name = "genero") String idGenero,
            @RequestParam(required = false, name = "orden") String orden
    ) {
        LOGGER.info("INSIDE FETCH_MOVIES -----> PELICULAS_CONTROLLER");

        if (nombre != null) {
            List<PeliculaResponseDto> peliculas = peliculaService.fetchPeliculaByTitulo(nombre);

            if (peliculas.isEmpty()) {
                return new ResponseEntity<>("No se encontraron peliculas con el nombre ingresado", HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(peliculas);
        }

        if (orden != null) {
            List<PeliculaResponseDto> peliculasOrdenadas = peliculaService.fetchPeliculaByOrder(orden);
            if (peliculasOrdenadas == null || peliculasOrdenadas.isEmpty()) {
                return new ResponseEntity<>("No se encontraron peliculas, verificar parametros de la query", HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(peliculasOrdenadas);
        }

        if (idGenero != null) {
            // TO DO
            return null;
        }

        List<PeliculaResponseDto> peliculas = peliculaService.fetchMovies();
        if (peliculas.isEmpty()) {
            return new ResponseEntity<>("No se encontraron peliculas", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(peliculas);

    }

}
