package com.challenge.alkemy.service;

import com.challenge.alkemy.entity.Genero;
import com.challenge.alkemy.entity.Pelicula;
import com.challenge.alkemy.entity.Personaje;
import com.challenge.alkemy.entity.dto.peliculaDto.PeliculaMapper;
import com.challenge.alkemy.entity.dto.peliculaDto.response.PeliculaConDetalleResponseDto;
import com.challenge.alkemy.repository.PeliculaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PeliculaServiceImpTest {

    private final String IMAGEN = "https://imagendeprueba.com.ar";

    @Mock
    private PeliculaRepository peliculaRepository;

    @Spy
    private PeliculaMapper peliculaMapper;

    @InjectMocks
    private PeliculaServiceImp peliculaServiceImp;


    @Test
    void getAllPeliculas() {

        // Given
        when(peliculaRepository.findAll()).thenReturn(Collections.singletonList(buildPelicula()));

        // When
        List<PeliculaConDetalleResponseDto> response = peliculaServiceImp.getAllPeliculas();

        // Then
        assertThat(response).isNotNull();
        assertThat(response.isEmpty()).isFalse();
        assertThat(response.get(0).getId()).isEqualTo(buildPelicula().getPeliculaId());
        verify(peliculaRepository, times(1)).findAll();
    }

    @Test
    void canGetAllPeliculasShouldReturnEmptyList() {

        // Given
        when(peliculaRepository.findAll()).thenReturn(Collections.emptyList());

        // When
        List<PeliculaConDetalleResponseDto> response = peliculaServiceImp.getAllPeliculas();

        // Then
        assertThat(response).isNotNull();
        assertThat(response.isEmpty()).isTrue();
        verify(peliculaRepository, times(1)).findAll();
    }

    @Test
    void getPeliculasByOrder() {


    }

    @Test
    void getPeliculasByGeneroId() {
    }

    @Test
    void deletePeliculaById() {
    }

    @Test
    void updatePelicula() {
    }

    @Test
    void addPersonajeToPelicula() {
    }

    @Test
    void deletePersonajeDePelicula() {
    }

    @Test
    void createPelicula() {
    }

    @Test
    void getPeliculaById() {
    }

    @Test
    void getPeliculasSinParametros() {
    }

    @Test
    void getPeliculaByTitulo() {
    }

    private Pelicula buildPelicula() {
        Pelicula pelicula1 = Pelicula.builder()
                .peliculaId(1L)
                .titulo("Ace Ventura")
                .imagen(IMAGEN)
                .calificacion(5)
                .fechaEstreno(Date.from(Instant.now()))
                .personajes(List.of(buildPersonaje()))
                .genero(buildGenero())
                .build();

        Pelicula pelicula2 = Pelicula.builder()
                .peliculaId(1L)
                .titulo("Westword")
                .imagen(IMAGEN)
                .calificacion(5)
                .fechaEstreno(Date.from(Instant.now()))
                .personajes(List.of(buildPersonaje()))
                .genero(buildGenero())
                .build();
        return null;
        // ToDo
    }

    private Genero buildGenero() {
        return Genero.builder()
                .generoId(1L)
                .nombre("Terror")
                .imagen("imagenDePrueba.jpg")
                .peliculas(Collections.emptyList())
                .build();
    }

    private Personaje buildPersonaje() {
        return Personaje.builder()
                .personajeId(1L)
                .nombre("PersonajeDePrueba")
                .imagen("Imagen de prueba")
                .historia("Historia de prueba sobre este personaje")
                .peso(35.6)
                .edad(23)
                .peliculas(Collections.emptyList())
                .build();
    }
}