package com.challenge.alkemy.repository;

import com.challenge.alkemy.entity.Genero;
import com.challenge.alkemy.entity.Pelicula;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AllArgsConstructor
class GeneroRepositoryTest {

    private GeneroRepository generoRepository;

    // Creamos un genero ficticio.


    @AfterEach
    void tearDown() {
        generoRepository.deleteAll();
    }



    @Test
    @DisplayName("Buscar Genero por nombre")
    void findGeneroByNombre() {
        // Guardamos en genero en la DB.

        Assertions.assertEquals("Terror", generoRepository.findGeneroByNombre("Terror").get().getNombre());
    }
}
