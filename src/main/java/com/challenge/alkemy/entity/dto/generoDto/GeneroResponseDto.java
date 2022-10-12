package com.challenge.alkemy.entity.dto.generoDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GeneroResponseDto {

    private String nombre;
    private Long id;
}
