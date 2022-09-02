package com.challenge.alkemy.repository;

import com.challenge.alkemy.entity.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {

}
