package com.challenge.alkemy.entity.dto.peliculaDto;

import com.challenge.alkemy.entity.Pelicula;
import com.challenge.alkemy.entity.Personaje;
import com.challenge.alkemy.entity.dto.generoDto.GeneroResponseDto;
import com.challenge.alkemy.entity.dto.peliculaDto.response.CreatePeliculaResponseDto;
import com.challenge.alkemy.entity.dto.peliculaDto.response.PeliculaBuscadaPorIdResponseDto;
import com.challenge.alkemy.entity.dto.peliculaDto.response.PeliculaBuscadaPorParametroResponseDto;
import com.challenge.alkemy.entity.dto.personajeDto.response.PersonajeResponseDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PeliculaMapper {

    public CreatePeliculaResponseDto peliculaToCreatePeliculaResponseDto(Pelicula pelicula) {
        return CreatePeliculaResponseDto.builder()
                .calificacion(pelicula.getCalificacion())
                .fechaEstreno(pelicula.getFechaEstreno())
                .genero(GeneroResponseDto.builder()
                        .nombre(pelicula.getGenero().getNombre())
                        .id(pelicula.getGenero().getGeneroId())
                        .build()
                )
                .id(pelicula.getPeliculaId())
                .titulo(pelicula.getTitulo())
                .imagen(pelicula.getImagen())
                .personajes(pelicula.getPersonajes().stream().map(personaje -> mapPersonajeToPersonajeDto(personaje)).collect(Collectors.toList()))
                .build();
    }

    // Este metodo lo usamos para mapear los personajes del metodo 'peliculaToCreatePeliculaResponseDto'
    private PersonajeResponseDto mapPersonajeToPersonajeDto(Personaje personaje) {
        return PersonajeResponseDto.builder()
                .imagen(personaje.getImagen())
                .nombre(personaje.getNombre())
                .build();
    }

    public PeliculaBuscadaPorIdResponseDto peliculaToPeliculaBuscadaPorIdDtoResponse(Pelicula pelicula) {
        return PeliculaBuscadaPorIdResponseDto.builder()
                .calificacion(pelicula.getCalificacion())
                .fechaEstreno(pelicula.getFechaEstreno())
                .genero(pelicula.getGenero())
                .id(pelicula.getPeliculaId())
                .titulo(pelicula.getTitulo())
                .imagen(pelicula.getImagen())
                .personajes(pelicula.getPersonajes())
                .build();
    }

    public PeliculaBuscadaPorParametroResponseDto peliculaToPeliculaBuscadaPorTituloDtoResponse(Pelicula pelicula) {

        PeliculaBuscadaPorParametroResponseDto peliculaEncontrada = new PeliculaBuscadaPorParametroResponseDto();

        peliculaEncontrada.setFechaEstreno(pelicula.getFechaEstreno());
        peliculaEncontrada.setTitulo(pelicula.getTitulo());
        peliculaEncontrada.setImagen(pelicula.getImagen());

        return peliculaEncontrada;
    }

    public List<PeliculaBuscadaPorParametroResponseDto> peliculaToPeliculaBuscadaPorParametroResponseDto(List<Pelicula> peliculas) {

        List<PeliculaBuscadaPorParametroResponseDto> peliculasDTO = new ArrayList<>();

        peliculas.forEach(source -> {
            PeliculaBuscadaPorParametroResponseDto peliculaAux =
            PeliculaBuscadaPorParametroResponseDto.builder()
                    .titulo(source.getTitulo())
                    .fechaEstreno(source.getFechaEstreno())
                    .imagen(source.getImagen())
                    .build();

            peliculasDTO.add(peliculaAux);
        });

        return peliculasDTO;
    }
}
