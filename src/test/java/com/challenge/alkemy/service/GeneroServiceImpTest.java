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
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
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

        // When (Aqui estamos haciendo el test)
        List<GeneroResponseDto> response = generoServiceImp.getAllGeneros();

        // Chequeamos cuantas veces llamamos al metodo generoRepository.findAll()
        verify(generoRepository, times(1)).findAll();

        assertAll(
                () -> assertThat(response).isNotNull(),
                () -> assertThat(response.get(0)).isNotNull(),
                () -> assertThat(response.get(0).getId()).isEqualTo(buildGenero().getGeneroId())
        );
    }

    @Test
    void canGetAllGenerosShoudReturnEmptyList() {

        // Given
        when(generoRepository.findAll()).thenReturn(Collections.emptyList());

        // When (Aqui estamos haciendo el test)
        List<GeneroResponseDto> response = generoServiceImp.getAllGeneros();

        // Then
        verify(generoRepository, times(1)).findAll();

        assertThat(response).isNotNull().isEqualTo(Collections.emptyList());
    }

    @Test
    void canSaveGenero() throws GeneroAlreadyInUseException, GeneroNotFoundException {

        // Aqui mockeamos que el usuario a crear no existe en la DB
        when(generoRepository.findGeneroByNombreContainingIgnoreCase(any())).thenReturn(Optional.empty());

        // Aqui mockeamos el genero que va a devolver el repository
        when(generoRepository.save(any())).thenReturn(buildGenero());

        // Aqui creamos el Dto que vamos a mandar al servicio
        CreateGeneroRequestDto request = buildCreateGeneroRequestDto();

        // Aqui realizamos el test
        CreateGeneroResponseDto response = generoServiceImp.saveGenero(request);

        // Then
        verify(generoRepository, times(1)).save(any());

        assertAll(
                () -> assertThat(response).isNotNull(),
                () -> assertThat(response.getId()).isEqualTo(1),
                () -> assertThat(response.getNombre()).isEqualTo(NOMBRE),
                () -> assertThat(response.getImagen()).isEqualTo(IMAGEN)
        );
    }


    @Test
    void saveGeneroWithoutNameShouldThrowException() {

        // Aqui creamos el Dto que vamos a mandar al servicio
        CreateGeneroRequestDto request = buildCreateGeneroRequestDto();
        request.setNombre("");

        // Then
        verify(generoRepository, times(0)).findAll();

        assertThatExceptionOfType(GeneroNotFoundException.class)
                .isThrownBy(() -> generoServiceImp.saveGenero(request));
    }

    @Test
    void saveGeneroAlreadyExistsShouldThrowException() throws GeneroAlreadyInUseException, GeneroNotFoundException {

        // Aqui mockeamos que el usuario a crear no existe en la DB
        when(generoRepository.findGeneroByNombreContainingIgnoreCase(any())).thenReturn(Optional.of(buildGenero()));

        // Aqui creamos el Dto que vamos a mandar al servicio
        CreateGeneroRequestDto request = buildCreateGeneroRequestDto();

        // Then
        verify(generoRepository, times(0)).findAll();

        assertThatExceptionOfType(GeneroAlreadyInUseException.class)
                .isThrownBy(() -> generoServiceImp.saveGenero(request));
    }

    @Test
    void canFindGeneroById() throws GeneroNotFoundException {

        Long id = 1L;
        Genero genero = buildGenero();

        when(generoRepository.findById(any())).thenReturn(Optional.of(genero));

        // When
        GeneroResponseDto response = generoServiceImp.getGeneroById(id);

        // Then
        verify(generoRepository, times(1)).findById(id);

        assertAll(
                () -> assertThat(response).isNotNull(),
                () -> assertThat(response).isEqualTo(generoMapper.generoToGeneroResponseDto(genero)),
                () -> assertThat(response.getId()).isEqualTo(id),
                () -> assertThat(response.getNombre()).isEqualTo(NOMBRE),
                () -> assertThat(response.getImagen()).isEqualTo(IMAGEN)
        );
    }

    @Test
    void generoByIdNotFoundShouldThrowException() throws GeneroNotFoundException {

        Long id = 1L;

        // Given
        when(generoRepository.findById(any())).thenReturn(Optional.empty());

        // Then
        assertThatExceptionOfType(GeneroNotFoundException.class)
                .isThrownBy(() -> generoServiceImp.getGeneroById(id));

        verify(generoRepository, times(1)).findById(id);
    }

    @Test
    void canDeleteGeneroById() throws GeneroNotFoundException {

        Genero genero = buildGenero();

        // Given
        when(generoRepository.findById(any())).thenReturn(Optional.of(genero));

        // When
        generoServiceImp.deleteGeneroById(genero.getGeneroId());
        ArgumentCaptor<Genero> generoArgumentCaptor =
                ArgumentCaptor.forClass(Genero.class);

        // Then
        verify(generoRepository).delete(generoArgumentCaptor.capture());
        assertThat(generoArgumentCaptor.getValue()).isEqualTo(genero);
    }

    @Test
    void generoToDeleteNotFoundById() {

        // Given
        when(generoRepository.findById(any())).thenReturn(Optional.empty());

        assertThatExceptionOfType(GeneroNotFoundException.class)
                .isThrownBy(() -> generoServiceImp.deleteGeneroById(1L));
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
}