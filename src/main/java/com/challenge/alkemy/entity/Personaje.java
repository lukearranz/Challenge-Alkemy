package com.challenge.alkemy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
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

    @Column(name = "imagen")
    @NotNull
    private String imagen;

    @Column(name = "nombre")
    @NotNull
    private String nombre;

    @Column(name = "edad")
    private int edad;

    @Column(name = "peso")
    private double peso;

    @Column(name = "historia")
    private String historia;

    /*
    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(
            name = "personajes_peliculas",
            joinColumns = @JoinColumn(
                    name = "personaje_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "pelicula_id"
            )
    )
     */


    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "personajes")
    @JsonIgnore
    private List<Pelicula> peliculas;
}
