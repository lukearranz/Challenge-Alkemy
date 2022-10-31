package com.challenge.alkemy.entity.dto.peliculaDto.request;

import com.challenge.alkemy.entity.Genero;
import com.challenge.alkemy.entity.Personaje;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class CreatePeliculaRequestDto {
    @NotBlank
    private String titulo;
    @NotNull
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date fechaEstreno;
    @NotBlank
    private String imagen; // ToDo Deberia ser de tipo URL
    @NotNull( message = "Calification cannot be null")
    @Min(value = 1 , message = "Calification should be between 1 and 5")
    @Max(value = 1 , message = "Calification should be between 1 and 5")
    private int calificacion;
    @NotEmpty
    private List<Long> personajesId; // Lista de los ID de los personajes
    @NotNull
    private Long generoId;
}