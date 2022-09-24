package com.challenge.alkemy.repository;

import com.challenge.alkemy.entity.Personaje;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonajeRepositoryTest {

    @Autowired
    private PersonajeRepository personajeRepository;

    Personaje personajeFicticio = Personaje.builder()
            .nombre("Carjulio")
            .edad(30)
            .historia("Fue picado por una araña y se convirtio en superHeroe")
            .imagen("https://upload.wikimedia.org/wikipedia/commons/9/90/Spiderman.JPG")
            .peso(65.0)
            .build();

    // Despues de correr cada test, eliminamos toda la data, asi el proximo se ejecuta con la DB limpia.
    @AfterEach
    void tearDown() {
        personajeRepository.deleteAll();
    }

    @Test
    public void checkIfPersonaCanBeSaved() {

        Personaje savedPersonaje = personajeRepository.save(personajeFicticio);
        Assertions.assertEquals(1L, savedPersonaje.getPersonajeId());
    }

    @Test
    void findByNombre() {

        Personaje personajeGuardadoEnDB = personajeRepository.save(personajeFicticio);

        Personaje personajeDB = personajeRepository.findByNombre(personajeFicticio.getNombre());

        Assertions.assertEquals(personajeGuardadoEnDB.getPersonajeId(), personajeDB.getPersonajeId());

    }

    @Test
    void findByEdad() {

    }

    @Test
    void findByPeso() {

    }
}