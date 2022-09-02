package com.challenge.alkemy.service;

import com.challenge.alkemy.entity.Pelicula;
import com.challenge.alkemy.repository.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PeliculaServiceImp implements PeliculaService{

    @Autowired
    private PeliculaRepository peliculaRepository;

    @Override
    public Pelicula savePelicula(Pelicula pelicula) {
        return peliculaRepository.save(pelicula);
    }
}
