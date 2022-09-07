package com.challenge.alkemy.service;

import com.challenge.alkemy.entity.Personaje;
import com.challenge.alkemy.error.PersonajeNotFoundException;
import com.challenge.alkemy.repository.PersonajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PersonajeServiceImp implements PersonajeService {

    @Autowired
    private PersonajeRepository personajeRepository;

    @Override
    public List<Personaje> fetchPersonajes() {
        return personajeRepository.findAll();
    }

    @Override
    public Personaje savePersonaje(Personaje personaje) {return personajeRepository.save(personaje);}

    @Override
    public Personaje fetchPersonajeById(Long personajeId) throws PersonajeNotFoundException {

        Optional<Personaje> personaje = personajeRepository.findById(personajeId);
        if (personaje.isEmpty()) {
            throw new PersonajeNotFoundException("No encontramos personaje con ese Id");
        }
        return personaje.get();
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

    @Override
    public List<Personaje> fetchPersonajeByNombre(String nombre) {
        return personajeRepository.findByNombre(nombre);
    }

    @Override
    public List<Personaje> fetchPersonajeByEdad(int edad) {
        return personajeRepository.findByEdad(edad);
    }

    //ToDo
    @Override
    public Object fetchPersonajeByPelicula(Long pelicula) {
        return null;
    }

    @Override
    public List<Personaje> fetchPersonajeByPeso(Double peso) {
        return personajeRepository.findByPeso(peso);
    }


}
