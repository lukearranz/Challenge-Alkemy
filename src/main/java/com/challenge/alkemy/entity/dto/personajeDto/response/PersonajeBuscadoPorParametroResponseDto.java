package com.challenge.alkemy.entity.dto.personajeDto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonajeBuscadoPorParametroResponseDto {
    private String nombre;
    private String imagen;
}
