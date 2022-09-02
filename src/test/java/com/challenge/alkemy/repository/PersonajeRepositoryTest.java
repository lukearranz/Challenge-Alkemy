package com.challenge.alkemy.repository;

import com.challenge.alkemy.entity.Personaje;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonajeRepositoryTest {

    @Autowired
    private PersonajeRepository personajeRepository;

    @Test
    public void savePersonaje() {
        Personaje personajeFicticio = Personaje.builder()
                .nombre("SpiderMan")
                .edad(27)
                .historia("Fue picado por una araña y se convirtio en super heroe")
                .imagen("https://upload.wikimedia.org/wikipedia/commons/9/90/Spiderman.JPG")
                .peso(65.0)
                .build();

        personajeRepository.save(personajeFicticio);
    }
}