package com.challenge.alkemy.repository;

import com.challenge.alkemy.entity.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {

    Optional<Pelicula> findPeliculaByTitulo(String titulo);
}
