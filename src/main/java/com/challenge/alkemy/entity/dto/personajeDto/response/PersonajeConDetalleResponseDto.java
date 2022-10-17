package com.challenge.alkemy.entity.dto.personajeDto.response;

import com.challenge.alkemy.entity.dto.peliculaDto.response.PeliculaEnPersonajeResponseDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PersonajeConDetalleResponseDto {

    private Long id;
    private String nombre;
    private String imagen;
    private int edad;
    private double peso;
    private String historia;

    private List<PeliculaEnPersonajeResponseDto> peliculas;

}
