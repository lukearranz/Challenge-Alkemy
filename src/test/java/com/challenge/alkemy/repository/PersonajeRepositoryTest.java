package com.challenge.alkemy.repository;

import com.challenge.alkemy.entity.Personaje;
import org.junit.jupiter.api.*;
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


    // Builders de personajes ficticios.
    Personaje personajeFicticio = Personaje.builder()
            .nombre("Carjulio")
            .edad(30)
            .historia("Fue picado por una araña y se convirtio en superHeroe")
            .imagen("https://upload.wikimedia.org/wikipedia/commons/9/90/Spiderman.JPG")
            .peso(97.8)
            .build();

    Personaje personajeFicticio2 = Personaje.builder()
            .nombre("Roman Riquelme")
            .edad(30)
            .historia("Fue picado por una araña y se convirtio en superHeroe")
            .imagen("https://upload.wikimedia.org/wikipedia/commons/9/90/Spiderman.JPG")
            .peso(89.9)
            .build();

    // Despues de correr cada test, eliminamos toda la data, asi el proximo se ejecuta con la DB limpia.
    @AfterEach
    void tearDown() {
        personajeRepository.deleteAll();
    }

    @Test
    @DisplayName("Verificar si un Personaje puede ser guardado")
    public void checkIfPersonaCanBeSaved() {
        Personaje savedPersonaje = personajeRepository.save(personajeFicticio);
        Assertions.assertEquals(personajeFicticio, savedPersonaje);
    }

    @Test
    @DisplayName("Buscar Personaje por nombre")
    void findByNombre() {

        //Personaje personajeGuardadoEnDB = personajeRepository.save(personajeFicticio);
        //Personaje personajeDB = personajeRepository.findByNombre(personajeFicticio.getNombre());

        //Assertions.assertEquals(personajeGuardadoEnDB.getPersonajeId(), personajeDB.getPersonajeId());
    }

    @Test
    @DisplayName("Buscar Personaje por edad")
    void findByEdad() {
        personajeRepository.save(personajeFicticio);
        //List<Personaje> personajesDB = personajeRepository.findByEdad(personajeFicticio.getEdad());

        //Assertions.assertEquals(30, personajesDB.get(0).getEdad());
    }

    @Test
    @DisplayName("Buscar Personaje por peso")
    void findByPeso() {
        personajeRepository.save(personajeFicticio2);
        //List<Personaje> personajesDB = personajeRepository.findByPeso(personajeFicticio2.getPeso());

        //Assertions.assertEquals(89.9, personajesDB.get(0).getPeso());
    }
}