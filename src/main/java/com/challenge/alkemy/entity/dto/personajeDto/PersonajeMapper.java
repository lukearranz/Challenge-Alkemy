package com.challenge.alkemy.entity.dto.personajeDto;

import com.challenge.alkemy.entity.Pelicula;
import com.challenge.alkemy.entity.Personaje;
import com.challenge.alkemy.entity.dto.generoDto.response.CreateGeneroResponseDto;
import com.challenge.alkemy.entity.dto.peliculaDto.response.PeliculaEnPersonajeResponseDto;
import com.challenge.alkemy.entity.dto.personajeDto.response.PersonajeBuscadoPorParametroResponseDto;
import com.challenge.alkemy.entity.dto.personajeDto.response.PersonajeConDetalleResponseDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PersonajeMapper {

    public PersonajeBuscadoPorParametroResponseDto personajeToPersonajeBuscadoPorParametroResponseDto(Personaje personaje) {

        return PersonajeBuscadoPorParametroResponseDto.builder()
                .nombre(personaje.getNombre())
                .imagen(personaje.getImagen())
                .build();
    }

    public List<PersonajeBuscadoPorParametroResponseDto> personajeToPersonajeBuscadoPorParametroResponseDto(List<Personaje> personajes) {

        List<PersonajeBuscadoPorParametroResponseDto> personajesMappeados = new ArrayList<>();
        for (Personaje personaje : personajes) {
            personajesMappeados.add(PersonajeBuscadoPorParametroResponseDto.builder()
                    .nombre(personaje.getNombre())
                    .imagen(personaje.getImagen())
                    .build());
        }
        return personajesMappeados;
    }

    public List<PersonajeConDetalleResponseDto> personajeToPersonajeConDetalleResponseDto(List<Personaje> personajes) {

        List<PersonajeConDetalleResponseDto> personajesMappeados = new ArrayList<>();
        for (Personaje personaje : personajes) {
            personajesMappeados.add(PersonajeConDetalleResponseDto.builder()
                    .id(personaje.getPersonajeId())
                    .nombre(personaje.getNombre())
                    .imagen(personaje.getImagen())
                    .edad(personaje.getEdad())
                    .peso(personaje.getPeso())
                    .historia(personaje.getHistoria())
                    .peliculas(personaje.getPeliculas().stream().map(pelicula -> mapPeliculaToPeliculaDto(pelicula)).collect(Collectors.toList()))
                    .build());
        }
        return personajesMappeados;
    }

    public PersonajeConDetalleResponseDto personajeToPersonajeConDetalleResponseDto(Personaje personaje) {

        return PersonajeConDetalleResponseDto.builder()
                .id(personaje.getPersonajeId())
                .nombre(personaje.getNombre())
                .imagen(personaje.getImagen())
                .edad(personaje.getEdad())
                .peso(personaje.getPeso())
                .historia(personaje.getHistoria())
                .peliculas(personaje.getPeliculas().stream().map(pelicula -> mapPeliculaToPeliculaDto(pelicula)).collect(Collectors.toList()))
                .build();
    }

    private PeliculaEnPersonajeResponseDto mapPeliculaToPeliculaDto(Pelicula pelicula) {

        return PeliculaEnPersonajeResponseDto.builder()
                .id(pelicula.getPeliculaId())
                .titulo(pelicula.getTitulo())
                .fechaEstreno(pelicula.getFechaEstreno())
                .imagen(pelicula.getImagen())
                .calificacion(pelicula.getCalificacion())
                .genero(CreateGeneroResponseDto.builder()
                        .id(pelicula.getGenero().getGeneroId())
                        .nombre(pelicula.getGenero().getNombre())
                        .imagen(pelicula.getGenero().getImagen())
                        .build())
                .build();
    }

}
