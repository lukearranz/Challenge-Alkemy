package com.challenge.alkemy.controller;

import com.challenge.alkemy.entity.Genero;
import com.challenge.alkemy.service.GeneroService;
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
public class GeneroController {

    @Autowired
    private GeneroService generoService;

    // Logger for debugging the application
    private final Logger LOGGER = LoggerFactory.getLogger(GeneroController.class);

    @Operation(summary = "Obtener todos los Generos")
    @GetMapping("/genero")
    public ResponseEntity<Object> fetchGenerosList() {
        LOGGER.info("INSIDE FETCH_GENEROS_LIST  ---->  GENERO_CONTROLLER");
        try {
            ResponseEntity<Object> response = generoService.fetchGenero();
            return response;
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Obtener un genero por Id")
    @GetMapping("/genero/{id}")
    public ResponseEntity<Object> fetchGeneroById(@PathVariable("id") Long generoId) {
        LOGGER.info("INSIDE FETCH_GENERO_BY_ID -----> GENERO_CONTROLLER");
        try {
            Optional<Genero> response = generoService.findGeneroById(generoId);
            if (response.isEmpty()) {
                return new ResponseEntity<>("NO SE ENCONTRO GENERO CON ESE ID", HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(generoService.findGeneroById(generoId));
        } catch (Exception e) {
            return new ResponseEntity<>("NO SE ENCONTRO GENERO CON ESE ID", HttpStatus.NOT_FOUND);
        }
    }

    // No esta funcionando bien, falla Hibernate al querer eliminar el Genero seleccionado.
    @Operation(summary = "Eliminar un genero por id")
    @DeleteMapping("/genero/{id}")
    public ResponseEntity<Object> deleteGeneroById(@PathVariable("id") Long generoId) {
        LOGGER.info("INSIDE DELETE_GENERO_BY_ID -----> GENERO_CONTROLLER");
        try {
            generoService.deleteGeneroById(generoId);
            return ResponseEntity.ok("GENERO ELIMINADO CON EXITO");
        } catch (Exception e) {
            return new ResponseEntity<>("ALGO SALIO MAL", HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Crear un nuevo Genero")
    @PostMapping("/genero")
    public ResponseEntity<Object> createGenero(@RequestBody Genero genero) {
            LOGGER.info("INSIDE CREATE_GENERO  ---->  GENERO_CONTROLLER");
            return generoService.saveGenero(genero);
    }




}
