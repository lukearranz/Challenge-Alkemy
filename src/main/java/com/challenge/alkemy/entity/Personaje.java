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
@Table(name = "PERSONAJE")
public class Personaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long personajeId;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "IMAGEN")
    private String imagen;

    @Column(name = "EDAD")
    private int edad;

    @Column(name = "PESO")
    private double peso;

    @Column(name = "HISTORIA")
    private String historia;

    @ManyToMany(mappedBy = "personajes")
    private List<Pelicula> peliculas;
}
