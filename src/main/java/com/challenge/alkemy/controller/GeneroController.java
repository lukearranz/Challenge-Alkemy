package com.challenge.alkemy.controller;

import com.challenge.alkemy.entity.Genero;
import com.challenge.alkemy.repository.GeneroRepository;
import com.challenge.alkemy.service.GeneroService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GeneroController {

    @Autowired
    private GeneroService generoService;

    // Logger for debugging the application
    private final Logger LOGGER = LoggerFactory.getLogger(GeneroController.class);

    @GetMapping("/genero")
    public ResponseEntity<Object> fetchGenerosList() {
        LOGGER.info("INSIDE FETCH_GENEROS_LIST  ---->  GENERO_CONTROLLER");
        try {
            return ResponseEntity.ok(generoService.fetchGenero());
        } catch (Exception e) {
            return new ResponseEntity<>("No se encontro ningun Género", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/genero")
    public ResponseEntity<Object> createGenero(@RequestBody Genero genero) {
            LOGGER.info("INSIDE CREATE_GENERO  ---->  GENERO_CONTROLLER");
            return generoService.saveGenero(genero);
    }


}
