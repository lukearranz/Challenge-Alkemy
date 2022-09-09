package com.challenge.alkemy.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PeliculaResponseDto {

    private String imagen;
    private String titulo;
    private Date fechaEstreno;

}
