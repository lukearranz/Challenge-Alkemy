package com.challenge.alkemy.repository;

import com.challenge.alkemy.entity.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GeneroRepository extends JpaRepository<Genero, Long> {

    Optional<Genero> findGeneroByNombre(String nombre);
}
