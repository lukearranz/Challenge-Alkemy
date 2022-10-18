package com.challenge.alkemy.repository;

import com.challenge.alkemy.entity.Genero;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AllArgsConstructor
class GeneroRepositoryTest {

    private GeneroRepository generoRepository;

    @AfterEach
    void tearDown() {
        generoRepository.deleteAll();
    }

    // Creamos un genero ficticio.
    Genero generoFicticio = Genero.builder()
            .nombre("Terror")
            .imagen("https://www.imagendeterror.com.ar")
            .build();

    @Test
    @DisplayName("Buscar Genero por nombre")
    void findGeneroByNombre() {
        // Guardamos en genero en la DB.
        generoRepository.save(generoFicticio);
        Assertions.assertEquals("Terror", generoRepository.findGeneroByNombre("Terror").get().getNombre());
    }
}
