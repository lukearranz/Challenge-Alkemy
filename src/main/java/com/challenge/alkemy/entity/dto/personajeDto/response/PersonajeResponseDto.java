package com.challenge.alkemy.entity.dto.personajeDto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonajeResponseDto {

    private String imagen;
    private String nombre;

}
