package com.challenge.alkemy.repository;

import com.challenge.alkemy.entity.Genero;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class GeneroRepositoryTest {

    private static final String NOMBRE = "Terror";
    private static final String IMAGEN = "https://lcsacsacsac";

    @Autowired
    private GeneroRepository generoRepository;

    // Creamos un genero ficticio.
    Genero generoFicticio = Genero.builder()
            .nombre(NOMBRE)
            .imagen(IMAGEN)
            .build();

    @BeforeEach
    void setUp() {
        generoRepository.save(generoFicticio);
    }

    @AfterEach
    void tearDown() { generoRepository.deleteAll(); }

    @Test
    void findGeneroByNombre() {

        Optional<Genero> genero = generoRepository.findGeneroByNombre(NOMBRE).stream().findFirst();
        Assertions.assertTrue(genero.isPresent());
        Assertions.assertEquals(genero.get().getNombre(), NOMBRE);
        Assertions.assertEquals(genero.get().getImagen(), IMAGEN);
    }

    @Test
    void findGeneroByNombreThatNotExists() {

        Optional<Genero> genero = generoRepository.findGeneroByNombre("Fruta");
        Assertions.assertFalse(genero.isPresent());

    }
}
