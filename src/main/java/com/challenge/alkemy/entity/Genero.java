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
@Table(name = "GENERO")
public class Genero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GENERO_ID")
    private Long generoId;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "IMAGEN")
    private String imagen;

    @OneToMany(mappedBy = "genero")
    private List<Pelicula> peliculas;
}