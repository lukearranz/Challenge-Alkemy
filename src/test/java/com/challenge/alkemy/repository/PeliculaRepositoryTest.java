package com.challenge.alkemy.repository;

import com.challenge.alkemy.entity.Genero;
import com.challenge.alkemy.entity.Pelicula;
import com.challenge.alkemy.entity.Personaje;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PeliculaRepositoryTest {

    @Autowired
    PeliculaRepository peliculaRepository;

    // Creamos un genero ficticio
    Genero generoFicticio = Genero.builder()
            .generoId(1L)
            .nombre("Terror")
            .imagen("gfdgfdgfdgfd")
            .build();

    // Creamos personajes ficticios
    Personaje personajeFicticioA = Personaje.builder()
            .personajeId(1)

            .build();

    // Creamos peliculas ficticia
    Pelicula peliculaFicticiaConA = Pelicula.builder()
            .imagen("http://google.com/fotosdegatitos.jpg")
            .calificacion(5)
            .titulo("Ace Ventura")
            .genero(generoFicticio)
            .build();

    Pelicula peliculaFicticiaConZ = Pelicula.builder()
            .imagen("http://google.com/fotosdegatitos.jpg")
            .calificacion(3)
            .titulo("Zeus")

            .genero(generoFicticio)
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

        Assertions.assertEquals(peliculaFicticiaConA.getTitulo(), peliculaRepository.findByTitulo("Ace Ventura"));
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

    @Test
    @DisplayName("Eliminar una Pelicula por ID")
    void deleteOnePeliculaById() {

        peliculaRepository.deleteById(peliculaFicticiaConA.getPeliculaId());
        Assertions.assertEquals(null, peliculaRepository.findByTitulo(peliculaFicticiaConA.getTitulo()));
    }
}