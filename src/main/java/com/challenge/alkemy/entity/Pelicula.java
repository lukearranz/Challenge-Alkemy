package com.challenge.alkemy.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pelicula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long peliculaId;
    @Column(name = "imagen")
    private String imagen;
    @Column(name = "titulo")
    private String titulo;

    @JsonFormat(pattern="dd-MM-yyyy")
    @Column(name = "fechaDeEstreno")
    private Date fechaEstreno;

    @NotNull
    @Min(value = 1, message = "La calificacion no puede ser menor a 1")
    @Max(value = 5, message = "La calificacion no puede ser mayor a 5")
    @Column(name = "calificacion")
    private int calificacion;


    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(
            name = "personajes_peliculas",
            joinColumns = @JoinColumn(
                    name = "pelicula_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "personaje_id"
            )
    )
    private List<Personaje> personajes;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="genero_id")
    private Genero genero;

}
