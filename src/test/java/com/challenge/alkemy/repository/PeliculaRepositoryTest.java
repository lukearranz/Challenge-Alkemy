package com.challenge.alkemy.repository;

import com.challenge.alkemy.entity.Pelicula;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PeliculaRepositoryTest {

    @Autowired
    PeliculaRepository peliculaRepository;

    // Creamos una pelicula ficticia
    Pelicula peliculaFicticiaConA = Pelicula.builder()
            .imagen("http://google.com/fotosdegatitos.jpg")
            .calificacion(5)
            .titulo("Ace Ventura")
            .build();

    Pelicula peliculaFicticiaConZ = Pelicula.builder()
            .imagen("http://google.com/fotosdegatitos.jpg")
            .calificacion(3)
            .titulo("Zeus")
            .build();

    @BeforeEach
    void savePelis() {
        peliculaRepository.save(peliculaFicticiaConA);
        peliculaRepository.save(peliculaFicticiaConZ);
    }

    @AfterEach
    void tearDown() {
        peliculaRepository.deleteAll();
    }

    @Test
    @DisplayName("Buscar Pelicula por titulo")
    void findByTitulo() {
        Assertions.assertEquals(peliculaFicticiaConA.getTitulo(), peliculaRepository.findByTitulo("Ace Ventura").get(0).getTitulo());
    }

    @Test
    @DisplayName("Ordenar Peliculas en orden ASC")
    void findAllByOrderByTituloAsc() {
        Assertions.assertEquals(peliculaFicticiaConA.getTitulo(), peliculaRepository.findAllByOrderByTituloAsc().get(0).getTitulo());
    }

    @Test
    @DisplayName("Ordenar Peliculas en orden DESC")
    void findAllByOrderByTituloDesc() {
        Assertions.assertEquals(peliculaFicticiaConZ.getTitulo(), peliculaRepository.findAllByOrderByTituloDesc().get(0).getTitulo());
    }
}