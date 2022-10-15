package com.challenge.alkemy.entity.dto.peliculaDto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Data
public class UpdatePeliculaRequestDto {
    @NotBlank
    private String titulo;
    @NotNull
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date fechaEstreno;
    @NotBlank
    private String imagen; // Deberia ser de tipo URL
    @NotNull( message = "Calification cannot be null")
    @Min(value = 1 , message = "Calification should be between 1 and 5")
    @Max(value = 1 , message = "Calification should be between 1 and 5")
    private int calificacion;
    @NotEmpty
    private List<Long> personajesId; // Lista de los ID de los personajes
    @NotNull
    private Long generoId;
}
