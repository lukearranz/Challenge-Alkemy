package com.challenge.alkemy.entity.dto.peliculaDto.response;

import com.challenge.alkemy.entity.Genero;
import com.challenge.alkemy.entity.Personaje;
import com.challenge.alkemy.entity.dto.generoDto.GeneroResponseDto;
import com.challenge.alkemy.entity.dto.personajeDto.response.PersonajeResponseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class CreatePeliculaResponseDto {
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
    private GeneroResponseDto genero;

    private List<PersonajeResponseDto> personajes;
}

