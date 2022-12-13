package com.challenge.alkemy.repository;

import com.challenge.alkemy.entity.Personaje;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
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
        assertThat(expected).isPresent().isNotEmpty();
        assertThat(expected.get().getEdad()).isEqualTo(personaje1.getEdad());
        assertThat(expected.get().getPeso()).isEqualTo(personaje1.getPeso());
        assertThat(expected.get().getHistoria()).isEqualTo(personaje1.getHistoria());
    }

    @Test
    void findByNombreIgnoreCaseNotPresent() {

        Optional<Personaje> expected = personajeRepository.findByNombreContainingIgnoreCase("AnToNiO BanDEraS");
        assertThat(expected).isEmpty().isNotPresent();
    }

    @Test
    void findByEdad() {

        generatePersonajes();
        Optional<List<Personaje>> expected = personajeRepository.findByEdad(45);
        assertThat(expected).isPresent().isNotEmpty();
        assertThat(expected.get().get(0).getEdad()).isEqualTo(personaje1.getEdad());
        assertThat(expected.get().get(0).getNombre()).isEqualTo(personaje1.getNombre());
        assertThat(expected.get().get(1).getEdad()).isEqualTo(personaje2.getEdad());
        assertThat(expected.get().get(1).getNombre()).isEqualTo(personaje2.getNombre());
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
        assertThat(expected).isPresent().isNotEmpty();
        assertThat(expected.get().get(0).getPeso()).isEqualTo(personaje2.getPeso());
        assertThat(expected.get().get(0).getNombre()).isEqualTo(personaje2.getNombre());
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