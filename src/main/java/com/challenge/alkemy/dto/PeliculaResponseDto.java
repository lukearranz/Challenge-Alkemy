package com.challenge.alkemy.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class PeliculaResponseDto {

    @NotBlank
    private String imagen;
    @NotBlank
    private String titulo;
    @NotNull
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date fechaEstreno;

}
