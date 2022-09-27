package com.challenge.alkemy.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
public class CreatePeliculaRequestDto {
    @NotBlank
    private String titulo;
    @NotNull
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date fechaEstreno;
    @NotBlank
    private String imagen;
    @NotNull( message = "Calification cannot be null")
    @Min(value = 1 , message = "Calification should be between 1 and 5")
    @Max(value = 1 , message = "Calification should be between 1 and 5")
    private int calificacion;
    @NotNull
    private Long generoId;

}