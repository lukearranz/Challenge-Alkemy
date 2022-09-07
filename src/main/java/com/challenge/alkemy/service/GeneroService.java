package com.challenge.alkemy.service;

import com.challenge.alkemy.entity.Genero;
import org.springframework.http.ResponseEntity;

public interface GeneroService {
    Object fetchGenero();

    ResponseEntity<Object> saveGenero(Genero genero);
}
