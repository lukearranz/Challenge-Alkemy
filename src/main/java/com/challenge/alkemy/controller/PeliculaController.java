package com.challenge.alkemy.controller;

import com.challenge.alkemy.dto.PeliculaResponseDto;
import com.challenge.alkemy.entity.Pelicula;
import com.challenge.alkemy.service.PeliculaService;
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

    @GetMapping("/pelicula")
    public ResponseEntity<Object> fetchPeliculas() {
        List<Pelicula> peliculas = peliculaService.fetchAllPeliculas();
        if (peliculas.isEmpty()) {
            return new ResponseEntity<>("No se encontraron personajes", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(peliculas);

    }

    @PostMapping("/pelicula")
    public ResponseEntity<Object> savePelicula(@RequestBody Pelicula pelicula) {
        LOGGER.info("INSIDE SAVE_PELICULA -----> PELICULA_CONTROLLER");
        return  peliculaService.savePelicula(pelicula);
    }

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

        List<PeliculaResponseDto> peliculas = peliculaService.fetchMovies();
        if (peliculas.isEmpty()) {
            return new ResponseEntity<>("No se encontraron peliculas", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(peliculas);

    }



}
