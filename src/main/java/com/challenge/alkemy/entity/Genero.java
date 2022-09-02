package com.challenge.alkemy.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Genero {
    @Id
    private Long generoId;
    private String nombre;
    private String imagen;
}
