package com.challenge.alkemy.entity.dto.peliculaDto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PeliculaBuscadaPorParametroResponseDto {

    private String titulo;
    private String imagen;
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date fechaEstreno;

}
