package com.challenge.alkemy.repository;

import com.challenge.alkemy.entity.Genero;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
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
        assertThat(expected).isNotEmpty();
        assertThat(expected).isPresent();
        assertThat(expected.get().getNombre()).isEqualTo(NOMBRE);
        assertThat(expected.get().getImagen()).isEqualTo(IMAGEN);
    }

    @Test
    void findByNonExistingGenero() {

        Optional<Genero> expected = generoRepository.findGeneroByNombreContainingIgnoreCase(GENERO_ERRONEO);
        assertThat(expected).isEmpty();
        assertThat(expected).isNotPresent();
    }

    @Test
    void findById() {

        Genero savedGenero = generoRepository.save(genereteGenero());
        Optional<Genero> expected = generoRepository.findById(savedGenero.getGeneroId());
        assertThat(expected).isPresent();
        assertThat(expected).isNotEmpty();
        assertThat(expected.get().getGeneroId()).isEqualTo(savedGenero.getGeneroId());
    }

    private Genero genereteGenero() {
        return Genero.builder()
                .nombre(NOMBRE)
                .imagen(IMAGEN)
                .build();
    }
}
