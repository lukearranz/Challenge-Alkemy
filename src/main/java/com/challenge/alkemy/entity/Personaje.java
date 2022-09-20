package com.challenge.alkemy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Personaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long personajeId;

    private String imagen;
    private String nombre;
    private int edad;
    private double peso;
    private String historia;

    @ManyToMany
    @JoinTable(
            name = "pelicula_personaje_map",
            joinColumns = @JoinColumn(
                    name = "personaje_id",
                    referencedColumnName = "personajeId"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "pelicula_id",
                    referencedColumnName = "peliculaId"
            )
    )
    private List<Pelicula> peliculas;
}
