package com.challenge.alkemy.repository;

import com.challenge.alkemy.entity.Genero;
import com.challenge.alkemy.entity.Pelicula;
import com.challenge.alkemy.entity.Personaje;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PeliculaRepositoryTest {


    // ToDo mock dependencies
    @Autowired
    PeliculaRepository peliculaRepository;
    @Autowired
    GeneroRepository generoRepository;
    @Autowired
    PersonajeRepository personajeRepository;

    private final String TITULO1 = "WoLF OF waLLStreet";
    private final String IMAGEN1 = "http://google.com/fotosdegatitos.jpg";
    private final String TITULO2 = "eL sEñoR de Los AnilLos";
    private final String IMAGEN2 = "http://google.com/asdasdasd.jpg";

    @AfterEach
    void tearDown() {
        peliculaRepository.deleteAll();
        generoRepository.deleteAll();
        personajeRepository.deleteAll();
    }

    @Test
    void findByTitulo() {

        peliculaRepository.save(generatePelicula(TITULO1, IMAGEN1));
        peliculaRepository.save(generatePelicula(TITULO2, IMAGEN2));
        Optional<Pelicula> expected = peliculaRepository.findByTituloContainingIgnoreCase(TITULO1);



        assertThat(expected).isNotEmpty().isPresent();
        assertThat(expected.get().getTitulo()).isEqualTo(TITULO1);
        assertThat(expected.get().getImagen()).isEqualTo(IMAGEN1);
    }

    @Test
    void findByTituloNotFound() {

        Optional<Pelicula> expected = peliculaRepository.findByTituloContainingIgnoreCase(TITULO1);
        assertThat(expected).isNotPresent().isEmpty();
    }

    @Test
    void findAllByOrderByTituloAsc() {

        peliculaRepository.save(generatePelicula(TITULO1, IMAGEN1));
        peliculaRepository.save(generatePelicula(TITULO2, IMAGEN2));

        List<Pelicula> expected = peliculaRepository.findAllByOrderByTituloAsc();

        assertThat(expected.get(0).getTitulo()).isEqualTo(TITULO1);
        assertThat(expected.get(0).getImagen()).isEqualTo(IMAGEN1);
    }


    @Test
    void findAllByOrderByTituloDesc() {

        peliculaRepository.save(generatePelicula(TITULO1, IMAGEN1));
        peliculaRepository.save(generatePelicula(TITULO2, IMAGEN2));

        List<Pelicula> expected = peliculaRepository.findAllByOrderByTituloDesc();
        assertThat(expected.get(0).getTitulo()).isEqualTo(TITULO2);
        assertThat(expected.get(0).getImagen()).isEqualTo(IMAGEN2);
    }



    private Pelicula generatePelicula(String titulo, String imagen) {
        Genero genero = generoRepository.save(
                Genero.builder()
                        .nombre("Accion")
                        .imagen("https://imagenDePruebaEnGenero")
                        .build());
        Personaje personaje1 = personajeRepository.save(Personaje.builder()
                .nombre("Roberto Carlos")
                .peso(65)
                .edad(30)
                .historia("Historia de prueba del personaje1")
                .imagen("http://imagenDePersonaje1.jpg")
                .build());
        Personaje personaje2 = personajeRepository.save(Personaje.builder()
                .nombre("Diego Maradona")
                .peso(80)
                .edad(60)
                .historia("Historia de prueba del personaje2")
                .imagen("http://imagenDePersonaje2.jpg")
                .build());
        return Pelicula.builder()
                .titulo(titulo)
                .imagen(imagen)
                .calificacion(3)
                .fechaEstreno(new Date())
                .genero(genero)
                .personajes(List.of(personaje1,personaje2))
                .build();
    }
}