package com.challenge.alkemy.entity.dto.personajeDto;

import com.challenge.alkemy.entity.Personaje;
import com.challenge.alkemy.entity.dto.personajeDto.response.PersonajeBuscadoPorParametroResponseDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonajeMapper {

    public PersonajeBuscadoPorParametroResponseDto personajeToPersonajeBuscadoPorParametroResponseDto(Personaje personaje) {
        return PersonajeBuscadoPorParametroResponseDto.builder()
                .nombre(personaje.getNombre())
                .imagen(personaje.getImagen())
                .build();
    }

    public List<PersonajeBuscadoPorParametroResponseDto> personajeToPersonajeBuscadoPorParametroResponseDto(List<Personaje> personajes) {

        List<PersonajeBuscadoPorParametroResponseDto> personajesMappeados = new ArrayList<>();
        for (Personaje personaje : personajes) {
            personajesMappeados.add(PersonajeBuscadoPorParametroResponseDto.builder()
                    .nombre(personaje.getNombre())
                    .imagen(personaje.getImagen())
                    .build());
        }
        return personajesMappeados;
    }

}
