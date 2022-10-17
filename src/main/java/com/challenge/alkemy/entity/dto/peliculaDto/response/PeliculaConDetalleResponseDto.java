package com.challenge.alkemy.entity.dto.peliculaDto.response;

import com.challenge.alkemy.entity.dto.generoDto.response.CreateGeneroResponseDto;
import com.challenge.alkemy.entity.dto.personajeDto.response.PersonajeConDetalleResponseDto;
import com.challenge.alkemy.entity.dto.personajeDto.response.PersonajeEnPeliculaResponseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class PeliculaConDetalleResponseDto {

    @NotNull
    private long id;
    @NotBlank
    private String titulo;
    @NotNull
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date fechaEstreno;
    @NotBlank
    private String imagen;
    @NotNull
    private int calificacion;
    @NotBlank
    private CreateGeneroResponseDto genero;

    private List<PersonajeEnPeliculaResponseDto> personajes;

}

