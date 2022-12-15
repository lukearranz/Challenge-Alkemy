package com.challenge.alkemy.repository;

import com.challenge.alkemy.entity.Genero;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GeneroRepositoryTest {

    private static final String NOMBRE = "teRRoR";
    private static final String IMAGEN = "https://imagenDePruebaEnGenero";
    private static final String GENERO_ERRONEO = "Accion";

    @Autowired
    private GeneroRepository generoRepository;

    @AfterEach
    void tearDown() { generoRepository.deleteAll(); }

    @Test
    void findOneByNombre() {

        generoRepository.save(genereteGenero());
        Optional<Genero> expected = generoRepository.findGeneroByNombreContainingIgnoreCase(NOMBRE).stream().findFirst();
        assertThat(expected).isNotEmpty().isPresent();
        assertThat(expected.get().getNombre()).isEqualTo(NOMBRE);
        assertThat(expected.get().getImagen()).isEqualTo(IMAGEN);
    }

    @Test
    void findByNonExistingGenero() {

        Optional<Genero> expected = generoRepository.findGeneroByNombreContainingIgnoreCase(GENERO_ERRONEO);
        assertThat(expected).isEmpty().isNotPresent();
    }

    @Test
    void findById() {

        Genero savedGenero = generoRepository.save(genereteGenero());
        Optional<Genero> expected = generoRepository.findById(savedGenero.getGeneroId());
        assertThat(expected).isPresent().isNotEmpty();
        assertThat(expected.get().getGeneroId()).isEqualTo(savedGenero.getGeneroId());
    }

    private Genero genereteGenero() {
        return Genero.builder()
                .nombre(NOMBRE)
                .imagen(IMAGEN)
                .build();
    }
}
