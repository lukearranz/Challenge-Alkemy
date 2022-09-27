package com.challenge.alkemy.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

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
    private String genero;

}

