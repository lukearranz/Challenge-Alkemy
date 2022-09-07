package com.challenge.alkemy.service;

import com.challenge.alkemy.entity.Genero;
import com.challenge.alkemy.repository.GeneroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class GeneroServiceImp implements GeneroService{

    @Autowired
    private GeneroRepository generoRepository;

    @Override
    public Object fetchGenero() {
        return generoRepository.findAll();
    }

    @Override
    public ResponseEntity<Object> saveGenero(Genero genero) {
        Optional<Genero> generoDB = generoRepository.findGeneroByNombre(genero.getNombre());
        if (generoDB.isEmpty()) {
            return ResponseEntity.ok(generoRepository.save(genero));
        }
        return new ResponseEntity<>("El Genero solicitado ya existe", HttpStatus.BAD_REQUEST);
    }
}
