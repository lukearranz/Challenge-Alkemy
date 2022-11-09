package com.challenge.alkemy.entity.dto.peliculaDto;

import com.challenge.alkemy.entity.Pelicula;
import com.challenge.alkemy.entity.Personaje;
import com.challenge.alkemy.entity.dto.generoDto.response.CreateGeneroResponseDto;
import com.challenge.alkemy.entity.dto.peliculaDto.response.PeliculaConDetalleResponseDto;
import com.challenge.alkemy.entity.dto.peliculaDto.response.PeliculaBuscadaPorParametroResponseDto;
import com.challenge.alkemy.entity.dto.personajeDto.response.PersonajeConDetalleResponseDto;
import com.challenge.alkemy.entity.dto.personajeDto.response.PersonajeEnPeliculaResponseDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PeliculaMapper {

    public PeliculaConDetalleResponseDto peliculaToDetallePeliculaResponseDto(Pelicula pelicula) {
        return PeliculaConDetalleResponseDto.builder()
                .calificacion(pelicula.getCalificacion())
                .fechaEstreno(pelicula.getFechaEstreno())
                .genero(CreateGeneroResponseDto.builder()
                        .nombre(pelicula.getGenero().getNombre())
                        .imagen(pelicula.getGenero().getImagen())
                        .id(pelicula.getGenero().getGeneroId())
                        .build())
                .id(pelicula.getPeliculaId())
                .titulo(pelicula.getTitulo())
                .imagen(pelicula.getImagen())
                .personajes(pelicula.getPersonajes().stream()
                        .map(personaje -> mapPersonajeToPersonajeDto(personaje))
                        .collect(Collectors.toList()))
                .build();
    }

    public PeliculaBuscadaPorParametroResponseDto peliculaToPeliculaBuscadaPorTituloDtoResponse(Pelicula pelicula) {

        return PeliculaBuscadaPorParametroResponseDto.builder()
                .fechaEstreno(pelicula.getFechaEstreno())
                .titulo(pelicula.getTitulo())
                .imagen(pelicula.getImagen())
                .build();
    }

    public List<PeliculaBuscadaPorParametroResponseDto> peliculaToPeliculaBuscadaPorParametroResponseDto(List<Pelicula> peliculas) {

        return peliculas.stream().map(pelicula -> PeliculaBuscadaPorParametroResponseDto.builder()
                .titulo(pelicula.getTitulo())
                .fechaEstreno(pelicula.getFechaEstreno())
                .imagen(pelicula.getImagen())
                .build()).collect(Collectors.toList());
    }

    // Este metodo lo usamos para mapear los personajes del metodo 'peliculaToCreatePeliculaResponseDto'
    private PersonajeEnPeliculaResponseDto mapPersonajeToPersonajeDto(Personaje personaje) {
        return PersonajeEnPeliculaResponseDto.builder()
                .id(personaje.getPersonajeId())
                .imagen(personaje.getImagen())
                .nombre(personaje.getNombre())
                .edad(personaje.getEdad())
                .peso(personaje.getPeso())
                .historia(personaje.getHistoria())
                .build();
    }
}
