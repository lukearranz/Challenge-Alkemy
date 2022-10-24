package com.challenge.alkemy.repository;

import com.challenge.alkemy.entity.Genero;
import com.challenge.alkemy.entity.Pelicula;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GeneroRepositoryTest {

    @Autowired
    private GeneroRepository generoRepository;

    // Creamos un genero ficticio.
    Genero generoFicticio = Genero.builder()
            .nombre("Terror")
            .imagen("imagenFicticiaDeGenero")
            .build();

    @BeforeEach
    void setUp() {
        generoRepository.save(generoFicticio);
    }

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
