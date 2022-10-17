package com.challenge.alkemy.entity.dto.personajeDto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonajeEnPeliculaResponseDto {

    private Long id;
    private String nombre;
    private String imagen;
    private int edad;
    private double peso;
    private String historia;

}
