package com.challenge.alkemy.entity.dto.generoDto.response;

import com.challenge.alkemy.entity.dto.peliculaDto.response.PeliculaBuscadaPorParametroResponseDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GeneroResponseDto {
    private Long id;
    private String nombre;
    private String imagen;
    private List<PeliculaBuscadaPorParametroResponseDto> peliculas;
}
