package com.challenge.alkemy.repository;

import com.challenge.alkemy.entity.Personaje;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.ARRAY;

@DataJpaTest
class PersonajeRepositoryTest {

    @Autowired
    private PersonajeRepository personajeRepository;

    Personaje personaje1 = Personaje.builder()
            .nombre("Antonio Banderas")
            .edad(45)
            .historia("Nacido en Málaga")
            .imagen("https://AntonioBanderas.jpg")
            .peso(70.20)
            .build();

    Personaje personaje2 = Personaje.builder()
            .nombre("Roman Riquelme")
            .edad(45)
            .historia("Ex jugador de Boca Juniors")
            .imagen("https://RomanRiquelme.jpg")
            .peso(89.9)
            .build();

    @AfterEach
    void tearDown() {
        personajeRepository.deleteAll();
    }

    @Test
    void findByNombreIgnoreCase() {

        generatePersonajes();
        Optional<Personaje> expected = personajeRepository.findByNombreContainingIgnoreCase("AnToNiO BanDEraS");
        assertThat(expected).isPresent();
        assertThat(expected).isNotEmpty();
        assertThat(expected.get().getEdad()).isEqualTo(personaje1.getEdad());
        assertThat(expected.get().getPeso()).isEqualTo(personaje1.getPeso());
        assertThat(expected.get().getHistoria()).isEqualTo(personaje1.getHistoria());
    }

    @Test
    void findByNombreIgnoreCaseNotPresent() {

        Optional<Personaje> expected = personajeRepository.findByNombreContainingIgnoreCase("AnToNiO BanDEraS");
        assertThat(expected).isEmpty();
        assertThat(expected).isNotPresent();
    }

    @Test
    void findByEdad() {

        generatePersonajes();
        Optional<List<Personaje>> expected = personajeRepository.findByEdad(45);
        assertThat(expected).isPresent();
        assertThat(expected).isNotEmpty();
        assertThat(expected.get()).contains(personaje2);
    }

    @Test
    void findByEdadNotPresent() {

        Optional<List<Personaje>> expected = personajeRepository.findByEdad(9999);
        assertThat(expected.get()).isEmpty();
    }

    @Test
    void findByPeso() {

        generatePersonajes();
        Optional<List<Personaje>> expected = personajeRepository.findByPeso(89.9);
        assertThat(expected).isPresent();
        assertThat(expected).isNotEmpty();
        assertThat(expected.get()).contains(personaje2);
    }

    @Test
    void findByPesoNotFound() {

        Optional<List<Personaje>> expected = personajeRepository.findByPeso(999.9);
        assertThat(expected.get()).isEmpty();
    }

    private void generatePersonajes() {

        List<Personaje> personajes = List.of(personaje1, personaje2);
        personajes.forEach(personaje -> personajeRepository.save(personaje));
    }
}