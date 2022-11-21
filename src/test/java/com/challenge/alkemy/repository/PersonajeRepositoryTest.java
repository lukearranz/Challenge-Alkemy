package com.challenge.alkemy.repository;

import com.challenge.alkemy.entity.Personaje;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

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

    @BeforeEach
    void setUp() {
        generatePersonajes();
    }

    @AfterEach
    void tearDown() {
        personajeRepository.deleteAll();
    }

    @Test
    void findByNombreIgnoreCase() {

        Optional<Personaje> expected = personajeRepository.findByNombreContainingIgnoreCase("AnToNiO BanDEraS");
        assertThat(expected).isPresent();
        assertThat(expected).isNotEmpty();
        assertThat(expected.get().getEdad()).isEqualTo(personaje1.getEdad());
        assertThat(expected.get().getPeso()).isEqualTo(personaje1.getPeso());
        assertThat(expected.get().getHistoria()).isEqualTo(personaje1.getHistoria());
    }


    @Test
    void findByEdad() {

        Optional<List<Personaje>> expected = personajeRepository.findByEdad(45);
        assertThat(expected).isPresent();
        assertThat(expected).isNotEmpty();
        assertThat(expected.get()).contains(personaje2);
    }


    @Test
    void findByPeso() {

        Optional<List<Personaje>> expected = personajeRepository.findByPeso(89.9);
        assertThat(expected).isPresent();
        assertThat(expected).isNotEmpty();
        assertThat(expected.get()).contains(personaje2);
    }

    private void generatePersonajes() {

        List<Personaje> personajes = List.of(personaje1, personaje2);
        personajes.forEach(personaje -> personajeRepository.save(personaje));
    }
}