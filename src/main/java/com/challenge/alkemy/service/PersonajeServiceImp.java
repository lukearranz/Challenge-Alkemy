package com.challenge.alkemy.service;

import com.challenge.alkemy.entity.Pelicula;
import com.challenge.alkemy.entity.dto.personajeDto.PersonajeMapper;
import com.challenge.alkemy.entity.dto.personajeDto.response.PersonajeBuscadoPorParametroResponseDto;
import com.challenge.alkemy.entity.dto.personajeDto.response.PersonajeResponseDto;
import com.challenge.alkemy.entity.Personaje;
import com.challenge.alkemy.error.personaje.PersonajeNotFoundException;
import com.challenge.alkemy.repository.PeliculaRepository;
import com.challenge.alkemy.repository.PersonajeRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PersonajeServiceImp implements PersonajeService {

    private PersonajeRepository personajeRepository;
    private ModelMapper modelMapper;
    private PersonajeMapper personajeMapper;
    private PeliculaRepository peliculaRepository;

    @Override
    public List<Personaje> fetchPersonajes() {
        return personajeRepository.findAll();
    }

    @Override
    public Personaje savePersonaje(Personaje personaje) {return personajeRepository.save(personaje);}

    @Override
    public Optional<Personaje> fetchPersonajeById(Long personajeId) {
        return personajeRepository.findById(personajeId);
    }

    @Override
    public void deletePersonajeById(Long personajeId) throws Exception {


        Personaje personajeDB = personajeRepository.findById(personajeId).orElseThrow(() -> new Exception("Personaje a eliminar por ID no encontrado"));

        personajeDB.getPeliculas().forEach(pelicula -> pelicula.getPersonajes().remove(personajeDB));

        personajeRepository.delete(personajeDB);
    }

    @Override
    public Personaje updatePersonaje(Long personajeId, Personaje personaje) {

        Personaje personajeDB = personajeRepository.findById(personajeId).get();

        // No necesitamos chequearlos por que no pueden ser nulos.
        personajeDB.setEdad((personaje.getEdad()));
        personajeDB.setPeso((personaje.getPeso()));

        if (Objects.nonNull(personaje.getNombre()) && !personaje.getNombre().isEmpty()) {
            personajeDB.setNombre((personaje.getNombre()));
        }

        if (Objects.nonNull(personaje.getImagen()) && !"".equalsIgnoreCase(personaje.getImagen())) {
            personajeDB.setImagen((personaje.getImagen()));
        }
        if (Objects.nonNull(personaje.getHistoria()) && !"".equalsIgnoreCase(personaje.getHistoria())) {
            personajeDB.setHistoria((personaje.getHistoria()));
        }

        return personajeRepository.save(personajeDB);
    }

    // Estos metodos devuelven DTO
    @Override
    public List<PersonajeBuscadoPorParametroResponseDto> fetchCharacters() {

        List<Personaje> personajesDB = personajeRepository.findAll();

        return personajeMapper.personajeToPersonajeBuscadoPorParametroResponseDto(personajesDB);
    }

    @Override
    public List<PersonajeBuscadoPorParametroResponseDto> fetchPersonajesByPeliculaId(Long idMovie) {

        Pelicula peliculaDB = peliculaRepository.findById(idMovie).orElseThrow();

        List<Personaje> personajesEnPelicula = peliculaDB.getPersonajes();

        return personajeMapper.personajeToPersonajeBuscadoPorParametroResponseDto(personajesEnPelicula);
    }

    @Override
    public PersonajeBuscadoPorParametroResponseDto fetchPersonajeByNombre(String nombre) throws PersonajeNotFoundException {
        Optional<Personaje> personajeEncontrado = personajeRepository.findByNombre(nombre);
        if (personajeEncontrado.isEmpty()) {
            throw new PersonajeNotFoundException("NO SE ENCONTRO PERSONAJE CON EL NOMBRE INDICADO");
        }
        return personajeMapper.personajeToPersonajeBuscadoPorParametroResponseDto(personajeEncontrado.get());
    }

    @Override
    public List<PersonajeBuscadoPorParametroResponseDto> fetchPersonajeByEdad(int edad) throws PersonajeNotFoundException {
        Optional<List<Personaje>> personajesDB = personajeRepository.findByEdad(edad);
        if (personajesDB.get().isEmpty()) {
            throw new PersonajeNotFoundException("NO SE ENCONTRO NINGUN PERSONAJE CON LA EDAD INDICADA");
        }
        return personajeMapper.personajeToPersonajeBuscadoPorParametroResponseDto(personajesDB.get());
    }

    @Override
    public List<PersonajeBuscadoPorParametroResponseDto> fetchPersonajeByPeso(Double peso) throws PersonajeNotFoundException {
        Optional<List<Personaje>> personajesDB = personajeRepository.findByPeso(peso);
        if (personajesDB.get().isEmpty()) {
            throw new PersonajeNotFoundException("NO SE ENCONTRO NINGUN PERSONAJE CON EL PESO INDICADO");
        }
        return personajeMapper.personajeToPersonajeBuscadoPorParametroResponseDto(personajesDB.get());
    }

}
