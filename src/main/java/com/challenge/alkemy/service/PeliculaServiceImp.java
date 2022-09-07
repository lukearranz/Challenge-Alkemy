package com.challenge.alkemy.service;

import com.challenge.alkemy.entity.Pelicula;
import com.challenge.alkemy.repository.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PeliculaServiceImp implements PeliculaService{

    @Autowired
    private PeliculaRepository peliculaRepository;

    @Override
    public ResponseEntity<Object> savePelicula(Pelicula pelicula) {
        Optional<Pelicula> peliculaDB = peliculaRepository.findPeliculaByTitulo(pelicula.getTitulo());
        if (peliculaDB.isEmpty()) {
            return ResponseEntity.ok(peliculaRepository.save(pelicula));
        }
        return new ResponseEntity<>("El Titulo solicitado ya existe", HttpStatus.BAD_REQUEST);
    }

    @Override
    public Optional<Pelicula> fetchPeliculaById(Long peliculaId) {
        Optional<Pelicula> pelicula = peliculaRepository.findById(peliculaId);
        if (pelicula.isEmpty()) {

        }
        return pelicula;
    }
}
