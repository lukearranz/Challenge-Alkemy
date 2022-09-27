package com.challenge.alkemy.repository;

import com.challenge.alkemy.entity.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {

    List<Pelicula> findByTitulo(String titulo);

    List<Pelicula> findAllByOrderByTituloAsc();

    List<Pelicula> findAllByOrderByTituloDesc();
}
