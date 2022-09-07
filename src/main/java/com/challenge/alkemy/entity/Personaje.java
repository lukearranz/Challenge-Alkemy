package com.challenge.alkemy.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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
    private List<Pelicula> peliculas;
}
