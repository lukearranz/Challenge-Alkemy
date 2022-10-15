package com.challenge.alkemy.entity.dto.generoDto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateGeneroResponseDto {
    private Long id;
    private String nombre;
    private String imagen;
}
