package com.challenge.alkemy.service;

import com.challenge.alkemy.entity.Pelicula;
import com.challenge.alkemy.entity.Personaje;
import com.challenge.alkemy.entity.dto.personajeDto.PersonajeMapper;
import com.challenge.alkemy.entity.dto.personajeDto.request.CreateOrUpdatePersonajeRequestDto;
import com.challenge.alkemy.entity.dto.personajeDto.response.PersonajeBuscadoPorParametroResponseDto;
import com.challenge.alkemy.entity.dto.personajeDto.response.PersonajeConDetalleResponseDto;
import com.challenge.alkemy.error.personaje.PersonajeNotFoundException;
import com.challenge.alkemy.error.personaje.PersonajeYaEnUsoException;
import com.challenge.alkemy.repository.PeliculaRepository;
import com.challenge.alkemy.repository.PersonajeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PersonajeServiceImp implements PersonajeService {

    private PersonajeRepository personajeRepository;
    private PersonajeMapper personajeMapper;
    private PeliculaRepository peliculaRepository;

    @Override
    public List<PersonajeConDetalleResponseDto> getAllPersonajes() {

        List<Personaje> personajes = personajeRepository.findAll();
        return personajeMapper.personajeToPersonajeConDetalleResponseDto(personajes);
    }

    @Override
    public PersonajeConDetalleResponseDto savePersonaje(CreateOrUpdatePersonajeRequestDto personajeRequest) throws PersonajeYaEnUsoException {

        if (personajeRepository.findByNombre(personajeRequest.getNombre()).isPresent()) {
            throw new PersonajeYaEnUsoException("EL PERSONAJE INDICADO YA EXISTE");
        }
        Personaje personajeToSave = Personaje.builder()
                .nombre(personajeRequest.getNombre())
                .imagen(personajeRequest.getImagen())
                .edad(personajeRequest.getEdad())
                .peso(personajeRequest.getPeso())
                .historia(personajeRequest.getHistoria())
                // Iniciamos las peliculas como un Array vacio, para evitar el 'null'
                .peliculas(new ArrayList<>())
                .build();
        return personajeMapper.personajeToPersonajeConDetalleResponseDto(personajeRepository.save(personajeToSave));
    }

    @Override
    public PersonajeConDetalleResponseDto getPersonajeById(Long personajeId) throws PersonajeNotFoundException {

        Personaje personajeDB = personajeRepository.findById(personajeId)
                .orElseThrow(()-> new PersonajeNotFoundException("NO SE ENCONTRO PERSONAJE CON EL ID INDICADO"));
        return personajeMapper.personajeToPersonajeConDetalleResponseDto(personajeDB);
    }

    @Override
    public void deletePersonajeById(Long personajeId) throws PersonajeNotFoundException {

        Personaje personajeDB = personajeRepository.findById(personajeId)
                .orElseThrow(() -> new PersonajeNotFoundException("NO SE ENCONTRO UN PERSONAJE CON ESE ID"));
        // Primero eliminamos el personaje de todas las peliculas
        personajeDB.getPeliculas().forEach(pelicula -> pelicula.getPersonajes().remove(personajeDB));
        personajeRepository.delete(personajeDB);
    }

    @Override
    public PersonajeConDetalleResponseDto updatePersonaje(Long personajeId, CreateOrUpdatePersonajeRequestDto personajeRequest) throws PersonajeNotFoundException {

        personajeRepository.findById(personajeId)
                .orElseThrow(()-> new PersonajeNotFoundException("NO SE ENCONTRO PERSONAJE A EDITAR CON ESE ID"));

        Personaje personajeToUpdate = Personaje.builder()
                .personajeId(personajeId)
                .nombre(personajeRequest.getNombre())
                .imagen(personajeRequest.getImagen())
                .edad(personajeRequest.getEdad())
                .peso(personajeRequest.getPeso())
                .historia(personajeRequest.getHistoria())
                // Iniciamos las peliculas como un Array vacio, para evitar el 'null'
                .peliculas(new ArrayList<>())
                .build();
        return personajeMapper.personajeToPersonajeConDetalleResponseDto(personajeRepository.save(personajeToUpdate));
    }

    @Override
    public List<PersonajeBuscadoPorParametroResponseDto> getPersonajes() {

        List<Personaje> personajesDB = personajeRepository.findAll();
        return personajeMapper.personajeToPersonajeBuscadoPorParametroResponseDto(personajesDB);
    }

    @Override
    public List<PersonajeBuscadoPorParametroResponseDto> getPersonajesByPeliculaId(Long idMovie) {

        Pelicula peliculaDB = peliculaRepository.findById(idMovie).orElseThrow();
        return personajeMapper.personajeToPersonajeBuscadoPorParametroResponseDto(peliculaDB.getPersonajes());
    }

    @Override
    public PersonajeBuscadoPorParametroResponseDto getPersonajeByNombre(String nombre) throws PersonajeNotFoundException {

        Personaje personajeEncontrado = personajeRepository.findByNombre(nombre)
                .orElseThrow(()-> new PersonajeNotFoundException("NO SE ENCONTRO PERSONAJE CON EL NOMBRE INDICADO"));
        return personajeMapper.personajeToPersonajeBuscadoPorParametroResponseDto(personajeEncontrado);
    }

    @Override
    public List<PersonajeBuscadoPorParametroResponseDto> getPersonajeByEdad(int edad) throws PersonajeNotFoundException {

        List<Personaje> personajesDB = personajeRepository.findByEdad(edad)
                .orElseThrow(()-> new PersonajeNotFoundException("NO SE ENCONTRO NINGUN PERSONAJE CON LA EDAD INDICADA"));
        return personajeMapper.personajeToPersonajeBuscadoPorParametroResponseDto(personajesDB);
    }

    @Override
    public List<PersonajeBuscadoPorParametroResponseDto> getPersonajeByPeso(Double peso) throws PersonajeNotFoundException {

        List<Personaje> personajesDB = personajeRepository.findByPeso(peso)
                .orElseThrow(()-> new PersonajeNotFoundException("NO SE ENCONTRO NINGUN PERSONAJE CON EL PESO INDICADO"));
        return personajeMapper.personajeToPersonajeBuscadoPorParametroResponseDto(personajesDB);
    }

}
