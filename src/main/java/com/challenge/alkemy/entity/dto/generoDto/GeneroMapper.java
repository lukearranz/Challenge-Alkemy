package com.challenge.alkemy.entity.dto.generoDto;

import com.challenge.alkemy.entity.Genero;
import com.challenge.alkemy.entity.Pelicula;
import com.challenge.alkemy.entity.dto.generoDto.response.CreateGeneroResponseDto;
import com.challenge.alkemy.entity.dto.generoDto.response.GeneroResponseDto;
import com.challenge.alkemy.entity.dto.peliculaDto.response.PeliculaBuscadaPorParametroResponseDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GeneroMapper {

    // Este metodo convierte una lista de Generos en una lista de GenerosResponseDto.
    public List<GeneroResponseDto> generosToGenerosResponseDto(List<Genero> generos) {
        List<GeneroResponseDto> generosDTO = new ArrayList<>();
        generos.forEach(genero -> {
            GeneroResponseDto generoAux = GeneroResponseDto.builder()
                    .id(genero.getGeneroId())
                    .imagen(genero.getImagen())
                    .nombre(genero.getNombre())
                    .peliculas(genero.getPeliculas().stream().map(pelicula -> mapPeliculaToPeliculaDto(pelicula)).collect(Collectors.toList()))
                    .build();
            generosDTO.add(generoAux);
        });
        return generosDTO;
    }

    // Este metodo convierte una Genero en un GeneroResponseDto.
    public GeneroResponseDto generoToGeneroResponseDto(Genero genero) {
        return GeneroResponseDto.builder()
                .id(genero.getGeneroId())
                .imagen(genero.getImagen())
                .nombre(genero.getNombre())
                .peliculas(genero.getPeliculas().stream().map(pelicula -> mapPeliculaToPeliculaDto(pelicula)).collect(Collectors.toList()))
                .build();
    }

    // Este metodo convierte un GeneroRequest en un GeneroResponseDto.
    public GeneroResponseDto generoRequestToGeneroResponseDto(Genero genero) {
        return GeneroResponseDto.builder()
                .id(genero.getGeneroId())
                .imagen(genero.getImagen())
                .nombre(genero.getNombre())
                .build();
    }

    public CreateGeneroResponseDto generoToCreateGeneroResponseDto(Genero genero) {
        return CreateGeneroResponseDto.builder()
                .id(genero.getGeneroId())
                .nombre(genero.getNombre())
                .imagen(genero.getImagen())
                .build();
    }

    // Este metodo transforma cada Pelicula en PeliculaBuscadaPorParametroResponseDto.
    private PeliculaBuscadaPorParametroResponseDto mapPeliculaToPeliculaDto(Pelicula pelicula) {
        return PeliculaBuscadaPorParametroResponseDto.builder()
                .imagen(pelicula.getImagen())
                .titulo(pelicula.getTitulo())
                .fechaEstreno(pelicula.getFechaEstreno())
                .build();
    }

}
