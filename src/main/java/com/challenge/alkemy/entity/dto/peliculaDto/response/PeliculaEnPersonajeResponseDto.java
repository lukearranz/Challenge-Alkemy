package com.challenge.alkemy.entity.dto.peliculaDto.response;

import com.challenge.alkemy.entity.dto.generoDto.response.CreateGeneroResponseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
public class PeliculaEnPersonajeResponseDto {

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

}
