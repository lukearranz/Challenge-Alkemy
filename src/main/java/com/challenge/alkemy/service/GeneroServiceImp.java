package com.challenge.alkemy.service;

import com.challenge.alkemy.entity.Genero;
import com.challenge.alkemy.repository.GeneroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GeneroServiceImp implements GeneroService{

    @Autowired
    private GeneroRepository generoRepository;

    @Override
    public ResponseEntity<Object> fetchGenero() {
        List<Genero> generosDB = generoRepository.findAll();
        if (generosDB.isEmpty()) {
            return new ResponseEntity<>("NO SE ENCONTRARON GENEROS",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(generosDB,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> saveGenero(Genero genero) {
        Optional<Genero> generoDB = generoRepository.findGeneroByNombre(genero.getNombre());
        if (generoDB.isEmpty()) {
            return ResponseEntity.ok(generoRepository.save(genero));
        }
        return new ResponseEntity<>("El Genero solicitado ya existe", HttpStatus.BAD_REQUEST);
    }

    @Override
    public Optional<Genero> findGeneroById(Long generoId) {
        return generoRepository.findById(generoId);
    }

    @Override
    public void deleteGeneroById(Long generoId) {
        generoRepository.deleteById(generoId);
    }
}
