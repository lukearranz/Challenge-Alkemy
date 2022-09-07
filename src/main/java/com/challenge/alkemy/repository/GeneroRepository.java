package com.challenge.alkemy.repository;

import com.challenge.alkemy.entity.Genero;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GeneroRepository extends JpaRepository<Genero, Long> {

    Optional<Genero> findGeneroByNombre(String nombre);
}
