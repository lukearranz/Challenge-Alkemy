package com.challenge.alkemy.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "PELICULA")
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long peliculaId;

    @Column(name = "TITULO")
    private String titulo;

    @Column(name = "IMAGEN")
    private String imagen;

    @JsonFormat(pattern="dd-MM-yyyy")
    @Column(name = "FECHA_DE_ESTRENO")
    private Date fechaEstreno;


    @Min(value = 1, message = "La calificacion no puede ser menor a 1")
    @Max(value = 5, message = "La calificacion no puede ser mayor a 5")
    @Column(name = "CALIFICACION")
    private int calificacion;

    @ManyToMany
    @JoinTable(
            name = "PERSONAJES_PELICULAS",
            joinColumns = @JoinColumn(
                    name = "PELICULA_ID"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "PERSONAJE_ID"
            )
    )
    private List<Personaje> personajes;

    @ManyToOne
    @JoinColumn(name ="genero_id")
    private Genero genero;

}

