package com.challenge.alkemy.service;

import com.challenge.alkemy.entity.Pelicula;
import com.challenge.alkemy.entity.Personaje;
import com.challenge.alkemy.entity.dto.personajeDto.PersonajeMapper;
import com.challenge.alkemy.entity.dto.personajeDto.request.CreateOrUpdatePersonajeRequestDto;
import com.challenge.alkemy.entity.dto.personajeDto.response.PersonajeBuscadoPorParametroResponseDto;
import com.challenge.alkemy.entity.dto.personajeDto.response.PersonajeConDetalleResponseDto;
import com.challenge.alkemy.error.personaje.PersonajeNotFoundException;
import com.challenge.alkemy.error.personaje.PersonajeYaEnUsoException;
import com.challenge.alkemy.repository.PeliculaRepository;
import com.challenge.alkemy.repository.PersonajeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonajeServiceImpTest {

    private final long id = 1L;
    private final String NOMBRE = "Carlos";
    private final String IMAGEN = "carlitos.jpg";
    private final String HISTORIA = "Historia ficticia";
    private final int EDAD = 70;
    private final double PESO = 69.5;

    @Mock
    private PersonajeRepository personajeRepository;

    @Mock
    private PeliculaRepository peliculaRepository;

    @Spy
    private PersonajeMapper personajeMapper;

    @InjectMocks
    private PersonajeServiceImp personajeServiceImp;

    @Test
    void canGetAllPersonajes() {

        // Given
        when(personajeRepository.findAll()).thenReturn(Collections.singletonList(buildPersonaje()));

        // When
        List<PersonajeConDetalleResponseDto> response = personajeServiceImp.getAllPersonajes();

        // Then
        verify(personajeRepository, times(1)).findAll();

        assertThat(response).isNotNull();
        assertThat(response.isEmpty()).isFalse();
        assertThat(response.get(0).getId().equals(buildPersonaje().getPersonajeId())).isTrue();
    }

    @Test
    void canGetAllPersonajesReturningEmptyList() {

        // Given
        when(personajeRepository.findAll()).thenReturn(Collections.emptyList());

        // When
        List<PersonajeConDetalleResponseDto> response = personajeServiceImp.getAllPersonajes();

        // Then
        verify(personajeRepository, times(1)).findAll();

        assertThat(response.isEmpty()).isTrue();
        assertThat(response).isNotNull();
    }

    @Test
    void canSavePersonaje() throws PersonajeYaEnUsoException {

        // Given
        when(personajeRepository.findByNombreContainingIgnoreCase(anyString())).thenReturn(Optional.empty());
        when(personajeRepository.save(any())).thenReturn(buildPersonaje());

        // When
        // Aqui creamos el Dto que vamos a mandar al servicio
        CreateOrUpdatePersonajeRequestDto request = buildPersonajeRequestDto();
        PersonajeConDetalleResponseDto response = personajeServiceImp.savePersonaje(request);

        // Then
        verify(personajeRepository, times(1)).save(any());

        assertThat(response).isEqualTo(personajeMapper.personajeToPersonajeConDetalleResponseDto(buildPersonaje()));
        assertThat(response).isNotNull();
        assertThat(response.getNombre()).isEqualTo(NOMBRE);
        assertThat(response.getEdad()).isEqualTo(EDAD);
        assertThat(response.getPeso()).isEqualTo(PESO);
        assertThat(response.getImagen()).isEqualTo(IMAGEN);
        assertThat(response.getHistoria()).isEqualTo(HISTORIA);
        assertThat(response.getPeliculas()).isEqualTo(Collections.emptyList());
        assertThat(response.getId()).isEqualTo(1L);
    }

    @Test
    void savePersonajeAlreadyExistsShouldReturnException() {

        // Given
        when(personajeRepository.findByNombreContainingIgnoreCase(anyString()))
                .thenReturn(Optional.of(buildPersonaje()));

        // When
        CreateOrUpdatePersonajeRequestDto request = buildPersonajeRequestDto();

        // Then
        verify(personajeRepository, times(0)).save(buildPersonaje());

        assertThatExceptionOfType(PersonajeYaEnUsoException.class)
                .isThrownBy(() -> personajeServiceImp.savePersonaje(request));
    }

    @Test
    void canGetPersonajeById() throws PersonajeNotFoundException {

        Personaje personaje = buildPersonaje();

        // Given
        when(personajeRepository.findById(anyLong())).thenReturn(Optional.of(personaje));

        // When
        PersonajeConDetalleResponseDto response = personajeServiceImp.getPersonajeById(id);

        // Then
        verify(personajeRepository, times(1)).findById(id);

        assertThat(response).isNotNull();
        assertThat(response).isEqualTo(personajeMapper.personajeToPersonajeConDetalleResponseDto(personaje));
        assertThat(response).isNotNull();
        assertThat(response.getNombre()).isEqualTo(NOMBRE);
        assertThat(response.getEdad()).isEqualTo(EDAD);
        assertThat(response.getPeso()).isEqualTo(PESO);
        assertThat(response.getImagen()).isEqualTo(IMAGEN);
        assertThat(response.getHistoria()).isEqualTo(HISTORIA);
        assertThat(response.getPeliculas()).isEqualTo(Collections.emptyList());
        assertThat(response.getId()).isEqualTo(1L);
    }

    @Test
    void persoajeByIdNotFoundShouldThrowException() {

        // Given
        when(personajeRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Then
        assertThatExceptionOfType(PersonajeNotFoundException.class)
                .isThrownBy(() -> personajeServiceImp.getPersonajeById(id));

        verify(personajeRepository, times(1)).findById(id);
    }

    @Test
    void canDeletePersonajeById() throws PersonajeNotFoundException {

        Long id = 1L;
        Personaje personaje = buildPersonaje();

        // Given
        when(personajeRepository.findById(anyLong())).thenReturn(Optional.of(personaje));

        // When
        personajeServiceImp.deletePersonajeById(personaje.getPersonajeId());
        ArgumentCaptor<Personaje> generoArgumentCaptor =
                ArgumentCaptor.forClass(Personaje.class);

        // Then
        verify(personajeRepository).delete(generoArgumentCaptor.capture());
        assertThat(generoArgumentCaptor.getValue()).isEqualTo(personaje);
    }

    @Test
    void canUpdatePersonaje() throws PersonajeNotFoundException {

        Long id = 1L;
        Personaje personaje = buildPersonaje();
        CreateOrUpdatePersonajeRequestDto personajeToUpdate = buildPersonajeRequestDto();
        personaje.setHistoria("Historia editada");
        personajeToUpdate.setHistoria("Historia editada");


        // Given
        when(personajeRepository.findById(anyLong())).thenReturn(Optional.of(personaje));
        when(personajeRepository.save(any())).thenReturn(personaje);

        // When
        PersonajeConDetalleResponseDto response = personajeServiceImp.updatePersonaje(id, personajeToUpdate);
        System.out.println(personajeToUpdate);
        System.out.println(personaje);
        System.out.println(response);

        // Then
        verify(personajeRepository, times(1)).findById(id);
        verify(personajeRepository, times(1)).save(personaje);

        assertThat(response).isEqualTo(personajeMapper.personajeToPersonajeConDetalleResponseDto(personaje));
        assertThat(response.getHistoria()).isEqualTo(personaje.getHistoria());
        assertThat(response.getPeso()).isEqualTo(personaje.getPeso());
        assertThat(response).isNotNull();
    }

    @Test
    void updatePersonajeNotFoundByIdShouldThrowException() {

        // Given
        when(personajeRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Then
        assertThatExceptionOfType(PersonajeNotFoundException.class)
                .isThrownBy(() -> personajeServiceImp.getPersonajeById(id));

        verify(personajeRepository, times(1)).findById(id);

    }

    @Test
    void canGetAllPersonajesWithParameters() {

        // Given
        when(personajeRepository.findAll()).thenReturn(Collections.singletonList(buildPersonaje()));

        // When
        List<PersonajeBuscadoPorParametroResponseDto> response = personajeServiceImp.getPersonajes();

        // Then
        verify(personajeRepository, times(1)).findAll();

        assertThat(response).isNotNull();
        assertThat(response.isEmpty()).isFalse();
    }

    @Test
    void getPersonajesByPeliculaId() {

        // Given
        when(peliculaRepository.findById(anyLong())).thenReturn(Optional.of(buildPelicula()));

        // When
        List<PersonajeBuscadoPorParametroResponseDto> response = personajeServiceImp.getPersonajesByPeliculaId(1L);

        // Then
        verify(peliculaRepository, times(1)).findById(anyLong());

        assertThat(response.get(0).getNombre()).isEqualTo(buildPersonaje().getNombre());
        assertThat(response.get(0).getImagen()).isEqualTo(buildPersonaje().getImagen());
    }

    @Test
    void personajesByPeliculaIdNotFoundShouldThrowException() {

        // Given
        when(peliculaRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Then
        assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(() -> personajeServiceImp.getPersonajesByPeliculaId(id));

        verify(peliculaRepository, times(1)).findById(id);
    }

    @Test
    void getPersonajeByNombre() throws PersonajeNotFoundException {

        Personaje personaje = buildPersonaje();

        // Given
        when(personajeRepository.findByNombreContainingIgnoreCase(anyString())).thenReturn(Optional.of(personaje));

        // When
        PersonajeBuscadoPorParametroResponseDto response = personajeServiceImp.getPersonajeByNombre(anyString());

        // Then
        assertThat(response).isNotNull();
        assertThat(response).isEqualTo(personajeMapper.personajeToPersonajeBuscadoPorParametroResponseDto(personaje));
        assertThat(response.getNombre()).isEqualTo(personaje.getNombre());
        assertThat(response.getImagen()).isEqualTo(personaje.getImagen());
        verify(personajeRepository, times(1)).findByNombreContainingIgnoreCase(anyString());
    }

    @Test
    void personajeByNombreNotFoundShouldThrowException() {

        // Given
        when(personajeRepository.findByNombreContainingIgnoreCase(anyString())).thenReturn(Optional.empty());

        // Then
        assertThatExceptionOfType(PersonajeNotFoundException.class)
                .isThrownBy(() -> personajeServiceImp.getPersonajeByNombre(anyString()));
        verify(personajeRepository, times(1)).findByNombreContainingIgnoreCase(anyString());
    }

    @Test
    void getPersonajeByEdad() throws PersonajeNotFoundException {

        Personaje personaje = buildPersonaje();

        // Given
        when(personajeRepository.findByEdad(anyInt())).thenReturn(Optional.of(Collections.singletonList(personaje)));

        // When
        List<PersonajeBuscadoPorParametroResponseDto> response = personajeServiceImp.getPersonajeByEdad(personaje.getEdad());

        // Then
        assertThat(response).isNotNull();
        assertThat(response.get(0)).isEqualTo(personajeMapper.personajeToPersonajeBuscadoPorParametroResponseDto(personaje));
        assertThat(response.isEmpty()).isFalse();
    }

    @Test
    void personajeByEdadNotFoundShouldThrowException() {

        // Given
        when(personajeRepository.findByEdad(anyInt())).thenReturn(Optional.empty());

        // Then
        assertThatExceptionOfType(PersonajeNotFoundException.class)
                .isThrownBy(() -> personajeServiceImp.getPersonajeByEdad(anyInt()));
        verify(personajeRepository, times(1)).findByEdad(anyInt());
    }

    @Test
    void getPersonajeByPeso() throws PersonajeNotFoundException {

        Personaje personaje = buildPersonaje();

        // Given
        when(personajeRepository.findByPeso(anyDouble()))
                .thenReturn(Optional.of(Collections.singletonList(personaje)));

        // When
        List<PersonajeBuscadoPorParametroResponseDto> response = personajeServiceImp.getPersonajeByPeso(personaje.getPeso());

        // Then
        assertThat(response).isNotNull();
        assertThat(response.isEmpty()).isFalse();
        assertThat(response.get(0)).isEqualTo(personajeMapper.personajeToPersonajeBuscadoPorParametroResponseDto(personaje));
        assertThat(response.get(0).getImagen()).isEqualTo(personaje.getImagen());
    }

    @Test
    void personajeByPesoNotFoundShouldThrowException() {

        // Given
        when(personajeRepository.findByPeso(anyDouble())).thenReturn(Optional.empty());

        // Then
        assertThatExceptionOfType(PersonajeNotFoundException.class)
                .isThrownBy(() -> personajeServiceImp.getPersonajeByPeso(anyDouble()));
        verify(personajeRepository, times(1)).findByPeso(anyDouble());
    }

    private Personaje buildPersonaje() {
        return Personaje.builder()
                .personajeId(1L)
                .nombre(NOMBRE)
                .imagen(IMAGEN)
                .peso(PESO)
                .edad(EDAD)
                .historia(HISTORIA)
                .peliculas(Collections.emptyList())
                .build();
    }

    private CreateOrUpdatePersonajeRequestDto buildPersonajeRequestDto() {
        return CreateOrUpdatePersonajeRequestDto.builder()
                .nombre(NOMBRE)
                .imagen(IMAGEN)
                .edad(EDAD)
                .historia(HISTORIA)
                .peso(PESO)
                .build();
    }

    private Pelicula buildPelicula() {
        return Pelicula.builder()
                .peliculaId(1L)
                .titulo("Ace Ventura")
                .fechaEstreno(Date.from(Instant.now()))
                .imagen("imagen.jpg")
                .personajes(Collections.singletonList(buildPersonaje()))
                .build();
    }
}