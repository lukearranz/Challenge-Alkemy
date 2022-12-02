package com.challenge.alkemy.service;

import com.challenge.alkemy.entity.Genero;
import com.challenge.alkemy.entity.Pelicula;
import com.challenge.alkemy.entity.Personaje;
import com.challenge.alkemy.entity.dto.peliculaDto.PeliculaMapper;
import com.challenge.alkemy.entity.dto.peliculaDto.response.PeliculaBuscadaPorParametroResponseDto;
import com.challenge.alkemy.entity.dto.peliculaDto.response.PeliculaConDetalleResponseDto;
import com.challenge.alkemy.error.genero.GeneroNotFoundException;
import com.challenge.alkemy.error.pelicula.PeliculaBuscadaPorParametroIncorrectoException;
import com.challenge.alkemy.error.pelicula.PeliculaNotFoundException;
import com.challenge.alkemy.repository.GeneroRepository;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PeliculaServiceImpTest {

    private final String IMAGEN = "https://imagendeprueba.com.ar";

    @Mock
    private PeliculaRepository peliculaRepository;
    @Mock
    private GeneroRepository generoRepository;

    @Spy
    private PeliculaMapper peliculaMapper;

    @InjectMocks
    private PeliculaServiceImp peliculaServiceImp;


    @Test
    void getAllPeliculas() {

        // Given
        when(peliculaRepository.findAll()).thenReturn(buildPelicula());

        // When
        List<PeliculaConDetalleResponseDto> response = peliculaServiceImp.getAllPeliculas();

        // Then
        assertThat(response).isNotNull();
        assertThat(response.isEmpty()).isFalse();
        assertThat(response.get(0).getId()).isEqualTo(buildPelicula().get(0).getPeliculaId());
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
    void canGetPeliculasByOrderAsc() throws PeliculaBuscadaPorParametroIncorrectoException, PeliculaNotFoundException {

        // Given
        when(peliculaRepository.findAllByOrderByTituloAsc())
                .thenReturn(Collections.singletonList(buildPelicula().get(0)));

        // When
        List<PeliculaBuscadaPorParametroResponseDto> response = peliculaServiceImp.getPeliculasByOrder("ASC");

        // Then
        verify(peliculaRepository, times(1)).findAllByOrderByTituloAsc();
        assertThat(response).isNotNull();
        assertThat(response.isEmpty()).isFalse();
        assertThat(response.get(0).getTitulo()).isEqualTo(buildPelicula().get(0).getTitulo());
    }

    @Test
    void peliculaByOrderAscNotFoundShouldThrowException() {

        // Given
        when(peliculaRepository.findAllByOrderByTituloAsc()).thenReturn(Collections.emptyList());

        // Then
        assertThatExceptionOfType(PeliculaNotFoundException.class)
                .isThrownBy(() -> peliculaServiceImp.getPeliculasByOrder("ASC"));
        verify(peliculaRepository, times(1)).findAllByOrderByTituloAsc();
    }

    @Test
    void canGetPeliculasByOrderDesc() throws PeliculaBuscadaPorParametroIncorrectoException, PeliculaNotFoundException {

        // Given
        when(peliculaRepository.findAllByOrderByTituloDesc())
                .thenReturn(Collections.singletonList(buildPelicula().get(1)));

        // When
        List<PeliculaBuscadaPorParametroResponseDto> response = peliculaServiceImp.getPeliculasByOrder("DESC");

        // Then
        verify(peliculaRepository, times(1)).findAllByOrderByTituloDesc();
        assertThat(response).isNotNull();
        assertThat(response.isEmpty()).isFalse();
        assertThat(response.get(0).getTitulo()).isEqualTo(buildPelicula().get(1).getTitulo());
    }

    @Test
    void peliculaByOrderDescNotFoundShouldThrowException() {

        // Given
        when(peliculaRepository.findAllByOrderByTituloDesc()).thenReturn(Collections.emptyList());

        // Then
        assertThatExceptionOfType(PeliculaNotFoundException.class)
                .isThrownBy(() -> peliculaServiceImp.getPeliculasByOrder("DESC"));
        verify(peliculaRepository, times(1)).findAllByOrderByTituloDesc();
    }

    @Test
    void peliculaByOrderWithWrongParameterShouldThrowException() {

        assertThatExceptionOfType(PeliculaBuscadaPorParametroIncorrectoException.class)
                .isThrownBy(() -> peliculaServiceImp.getPeliculasByOrder("ASCS"));
        verify(peliculaRepository, times(0)).findAllByOrderByTituloDesc();
        verify(peliculaRepository, times(0)).findAllByOrderByTituloAsc();
    }

    @Test
    void canGetPeliculasByGeneroId() throws GeneroNotFoundException {

        List<Pelicula> peliculas = buildPelicula();
        Genero genero = buildGenero();
        genero.setPeliculas(peliculas);

        // Given
        when(generoRepository.findById(anyLong())).thenReturn(Optional.of(genero));

        // When
        List<PeliculaBuscadaPorParametroResponseDto> response = peliculaServiceImp.getPeliculasByGeneroId(1L);

        // Then
        assertThat(response.get(0).getTitulo()).isEqualTo(genero.getPeliculas().get(0).getTitulo());
        assertThat(response.get(0).getTitulo()).isEqualTo(genero.getPeliculas().get(0).getTitulo());
        assertThat(response).isNotNull();
        assertThat(response.isEmpty()).isFalse();
        verify(generoRepository, times(1)).findById(anyLong());
    }

    @Test
    void getPeliculasByGeneroIdNotFoundThrowException() {

        // Given
        when(generoRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Then
        assertThatExceptionOfType(GeneroNotFoundException.class)
                .isThrownBy(() -> peliculaServiceImp.getPeliculasByGeneroId(anyLong()));
        verify(generoRepository, times(1)).findById(anyLong());
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

    private List<Pelicula> buildPelicula() {
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

        return List.of(pelicula1, pelicula2);
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