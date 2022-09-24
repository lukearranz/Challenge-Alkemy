package com.challenge.alkemy.service;

import com.challenge.alkemy.dto.PersonajeResponseDto;
import com.challenge.alkemy.entity.Personaje;
import com.challenge.alkemy.repository.PersonajeRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PersonajeServiceImp implements PersonajeService {

    @Autowired
    private PersonajeRepository personajeRepository;

    @Autowired
    private ModelMapper modelMapper;

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
    public void deletePersonajeById(Long personajeId) {
                personajeRepository.deleteById(personajeId);
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
    public List<PersonajeResponseDto> fetchCharacters() {
        return convertEntityToDto(personajeRepository.findAll());
    }

    @Override
    public List<PersonajeResponseDto> fetchPersonajesByPeliculaId(Long idMovie) {

        // To Do

        return null;
    }

    @Override
    public List<PersonajeResponseDto> fetchPersonajeByNombre(String nombre) {
        return convertEntityToDto((List<Personaje>) personajeRepository.findByNombre(nombre));
    }

    @Override
    public List<PersonajeResponseDto> fetchPersonajeByEdad(int edad) {
        return convertEntityToDto(personajeRepository.findByEdad(edad));
    }

    @Override
    public List<PersonajeResponseDto> fetchPersonajeByPeso(Double peso) {
        return convertEntityToDto(personajeRepository.findByPeso(peso));
    }

    //ToDo
    @Override
    public Object fetchPersonajeByPelicula(Long pelicula) {

        return null;
    }




    // Este metodo recibe una lista de Personajes y la transforma en una lista de PersonajeResponseDto
    private List<PersonajeResponseDto> convertEntityToDto(List<Personaje> personajes) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        List<PersonajeResponseDto> personajeDTO = new ArrayList<>();

        for (Personaje personaje : personajes) {
            personajeDTO.add(modelMapper.map(personaje, PersonajeResponseDto.class));
        }

        return personajeDTO;
    }

}
