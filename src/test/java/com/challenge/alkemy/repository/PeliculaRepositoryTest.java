package com.challenge.alkemy.repository;

import com.challenge.alkemy.entity.Genero;
import com.challenge.alkemy.entity.Pelicula;
import com.challenge.alkemy.entity.Personaje;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PeliculaRepositoryTest {

    @Autowired
    PeliculaRepository peliculaRepository;

    @Autowired
    GeneroRepository generoRepository;

    @Autowired
    PersonajeRepository personajeRepository;

    // Creamos un genero ficticio a guardar
    Genero generoFicticio = Genero.builder()
            .nombre("Terror")
            .imagen("https://imagenDeGeneroFicticio.jpg")
            .build();

    // Creamos personajes ficticios a guardar
    Personaje personajeFicticioA = Personaje.builder()
            .nombre("Personaje Ficticio Uno")
            .peso(65)
            .edad(30)
            .historia("Viene de Rosario, Argentina")
            .imagen("http://imagenDePersonaje.jpg")
            .build();

    Personaje personajeFicticioB = Personaje.builder()
            .nombre("Personaje Ficticio Dos")
            .peso(65)
            .edad(30)
            .historia("Viene de Nogoya, Argentina")
            .imagen("http://imagenDePersonaje.jpg")
            .build();

    // Creamos peliculas ficticia
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

        Genero generoGuardado = generoRepository.save(generoFicticio);
        Personaje personajeGuardado1 = personajeRepository.save(personajeFicticioA);
        Personaje personajeGuardado2 = personajeRepository.save(personajeFicticioB);

        List<Personaje> personajesFicticios = new ArrayList<>();
        personajesFicticios.add(personajeGuardado1);
        personajesFicticios.add(personajeGuardado2);

        peliculaFicticiaConA.setGenero(generoGuardado);
        peliculaFicticiaConA.setPersonajes(personajesFicticios);
        peliculaFicticiaConZ.setPersonajes(personajesFicticios);

        peliculaFicticiaConZ.setGenero(generoGuardado);

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
        Pelicula peliculaDB = peliculaRepository.findByTitulo("Ace Ventura").orElseThrow();
        Assertions.assertEquals(peliculaFicticiaConA.getTitulo(), peliculaDB.getTitulo());
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