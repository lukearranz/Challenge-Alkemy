package com.challenge.alkemy.service;

import com.challenge.alkemy.entity.Genero;
import com.challenge.alkemy.entity.dto.generoDto.GeneroMapper;
import com.challenge.alkemy.entity.dto.generoDto.request.CreateGeneroRequestDto;
import com.challenge.alkemy.entity.dto.generoDto.response.CreateGeneroResponseDto;
import com.challenge.alkemy.entity.dto.generoDto.response.GeneroResponseDto;
import com.challenge.alkemy.error.genero.GeneroAlreadyInUseException;
import com.challenge.alkemy.error.genero.GeneroNotFoundException;
import com.challenge.alkemy.repository.GeneroRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GeneroServiceImpTest {

    private final String NOMBRE = "Carlos";
    private final String IMAGEN = "https://blablabla.com";

    @Mock
    private GeneroRepository generoRepository;

    @Spy
    private GeneroMapper generoMapper = new GeneroMapper();

    @InjectMocks
    private GeneroServiceImp generoServiceImp;


    @Test
    void canGetAllGeneros() {

        // Given
        when(generoRepository.findAll()).thenReturn(Collections.singletonList(buildGenero()));

        // When
        List<GeneroResponseDto> response = generoServiceImp.getAllGeneros();

        // Then
        verify(generoRepository, times(1)).findAll();

        assertThat(response).isNotNull();
        assertThat(response.isEmpty()).isFalse();
        assertThat(response.get(0).getId().equals(buildGenero().getGeneroId())).isTrue();
    }

    @Test
    void whenFindAllThenReturnEmpltyList() {

        // Given
        when(generoRepository.findAll()).thenReturn(Collections.emptyList());

        // When
        List<GeneroResponseDto> response = generoServiceImp.getAllGeneros();

        // Then
        verify(generoRepository, times(1)).findAll();

        assertThat(response).isNotNull();
        assertThat(response.isEmpty()).isTrue();
    }

    @Test
    void canSaveGenero() throws GeneroAlreadyInUseException, GeneroNotFoundException {

        // Given
        when(generoRepository.findGeneroByNombreContainingIgnoreCase(any())).thenReturn(Optional.empty());
        when(generoRepository.save(any())).thenReturn(buildGenero());

        CreateGeneroRequestDto request = buildCreateGeneroRequestDto();

        // When
        CreateGeneroResponseDto response = generoServiceImp.saveGenero(request);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1);
        assertThat(response.getImagen()).isEqualTo(IMAGEN);

    }

    private Genero buildGenero() {
        return Genero.builder()
                .generoId(1L)
                .imagen(IMAGEN)
                .nombre(NOMBRE)
                .peliculas(Collections.emptyList())
                .build();
    }

    private Genero buildGeneroToSave() {
        return Genero.builder()
                .imagen(IMAGEN)
                .nombre(NOMBRE)
                .peliculas(Collections.emptyList())
                .build();
    }

    private CreateGeneroRequestDto buildCreateGeneroRequestDto() {
        return CreateGeneroRequestDto.builder()
                .imagen(IMAGEN)
                .nombre(NOMBRE)
                .peliculasId(Collections.emptyList())
                .build();
    }

    private CreateGeneroResponseDto buildCreateGeneroResponseDto() {
        return CreateGeneroResponseDto.builder()
                .id(1L)
                .nombre(NOMBRE)
                .imagen(IMAGEN)
                .build();
    }



}