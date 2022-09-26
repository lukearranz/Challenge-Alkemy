package com.challenge.alkemy.repository;

import com.challenge.alkemy.entity.Personaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonajeRepository extends JpaRepository<Personaje, Long> {

    Personaje findByNombre(String nombre);

    List<Personaje> findByEdad(int edad);

    List<Personaje> findByPeso(Double peso);

}
