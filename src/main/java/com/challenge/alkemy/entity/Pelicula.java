package com.challenge.alkemy.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pelicula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long peliculaId;
    private String imagen;
    private String titulo;

    @JsonFormat(pattern="dd/MM/yyyy")
    private Date fechaEstreno;

    private int calificacion;

    @ManyToMany
    private List<Personaje> personajes;

    @ManyToOne
    private Genero genero;
}
