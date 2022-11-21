package com.challenge.alkemy.repository;

import com.challenge.alkemy.entity.Personaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonajeRepository extends JpaRepository<Personaje, Long> {

    Optional<Personaje> findByNombreContainingIgnoreCase(String nombre);

    Optional<List<Personaje>> findByEdad(int edad);

    Optional<List<Personaje>> findByPeso(Double peso);

}
