package com.challenge.alkemy.entity.dto.peliculaDto.response;

import com.challenge.alkemy.entity.Genero;
import com.challenge.alkemy.entity.Personaje;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PeliculaBuscadaPorIdResponseDto {

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
    private Genero genero;

    private List<Personaje> personajes;
}
