package com.challenge.alkemy.service;

import com.challenge.alkemy.dto.PeliculaResponseDto;
import com.challenge.alkemy.dto.PersonajeResponseDto;
import com.challenge.alkemy.entity.Pelicula;
import com.challenge.alkemy.entity.Personaje;
import com.challenge.alkemy.repository.PeliculaRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PeliculaServiceImp implements PeliculaService{

    @Autowired
    private PeliculaRepository peliculaRepository;

    @Autowired
    private PersonajeService personajeService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<Object> fetchAllPeliculas() {
        List<Pelicula> peliculasDB = peliculaRepository.findAll();
        if (peliculasDB.isEmpty()) {
            return new ResponseEntity<>("NO SE ENCONTRARON PELICULAS", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(peliculasDB, HttpStatus.OK);
    }

    @Override
    public List<PeliculaResponseDto> fetchPeliculaByOrder(String orden) {
        if (orden.equals("ASC")) {
            return convertEntityToDto(peliculaRepository.findAllByOrderByTituloAsc());
        }

        if (orden.equals("DESC")) {
            return convertEntityToDto(peliculaRepository.findAllByOrderByTituloDesc());
        }

        return null;

    }

    @Override
    public void deletePeliculaById(Long peliculaId) {
        peliculaRepository.deleteById(peliculaId);
    }

    @Override
    public Object updatePelicula(Long peliculaId, Pelicula pelicula) {

        Pelicula peliculaDB = peliculaRepository.findById(peliculaId).get();

        if (Objects.nonNull(pelicula.getTitulo()) && !pelicula.getTitulo().isEmpty()) {
            peliculaDB.setTitulo(pelicula.getTitulo());
        }
        if (Objects.nonNull(pelicula.getImagen()) && !"".equalsIgnoreCase(pelicula.getImagen())) {
            peliculaDB.setImagen((pelicula.getImagen()));
        }
        peliculaDB.setCalificacion(pelicula.getCalificacion());
        peliculaDB.setFechaEstreno(pelicula.getFechaEstreno());
        // Testing
        peliculaDB.setPersonajes(pelicula.getPersonajes());
        peliculaDB.setGenero(pelicula.getGenero());

        return peliculaRepository.save(peliculaDB);

    }

    @Override
    public ResponseEntity<Object> addCharacterToMovie(Long idMovie, Long idCharacter) {

        Personaje personajeToAdd = personajeService.fetchPersonajeById(idCharacter).get();
        Pelicula peliculaDB = fetchPeliculaById(idMovie).get();

        List<Personaje> personajesInMovie = peliculaDB.getPersonajes();

        if (personajesInMovie.contains(personajeToAdd)) {
            return new ResponseEntity<>("EL PERSONAJE QUE INTENTA AGREGAR YA EXISTE EN ESTA PELICULA",HttpStatus.BAD_REQUEST);
        }

        personajesInMovie.add(personajeToAdd);

        peliculaDB.setPersonajes(personajesInMovie);

        return new ResponseEntity<>(peliculaRepository.save(peliculaDB), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> deleteCharacterFromMovie(Long idMovie, Long idCharacter) {

        Personaje personajeToDelete = personajeService.fetchPersonajeById(idCharacter).get();
        Pelicula peliculaDB = fetchPeliculaById(idMovie).get();

        List<Personaje> personajesInMovie = peliculaDB.getPersonajes();

        if (personajesInMovie.contains(personajeToDelete)) {
            personajesInMovie.remove(personajeToDelete);
            peliculaDB.setPersonajes(personajesInMovie);
            peliculaRepository.save(peliculaDB);
            return new ResponseEntity<>("PERSONAJE ELIMINADO CON EXITO", HttpStatus.OK);
        }
        return new ResponseEntity<>("EL PERSONAJE QUE DESEA ELIMINAR NO SE ENCUENTRA EN ESTA PELICULA" , HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Object> savePelicula(Pelicula pelicula) {
        if (pelicula.getCalificacion() < 1 || pelicula.getCalificacion() > 5) {
            return new ResponseEntity<>("LA CALIFICACION DEBE ESTAR ENTRE 1 Y 5", HttpStatus.BAD_REQUEST);
        }
        List<Pelicula> peliculaDB = peliculaRepository.findByTitulo(pelicula.getTitulo());
        if (peliculaDB.isEmpty()) {
            return ResponseEntity.ok(peliculaRepository.save(pelicula));
        }
        return new ResponseEntity<>("EL TITULO SOLICITADO YA EXISTE", HttpStatus.BAD_REQUEST);
    }

    @Override
    public Optional<Pelicula> fetchPeliculaById(Long peliculaId) {
        return peliculaRepository.findById(peliculaId);

    }

    @Override
    public List<PeliculaResponseDto> fetchMovies() {
        return convertEntityToDto(peliculaRepository.findAll());
    }

    @Override
    public List<PeliculaResponseDto> fetchPeliculaByTitulo(String titulo) {
        return convertEntityToDto(peliculaRepository.findByTitulo(titulo));
    }

    // Este metodo recibe una lista de Personajes y la transforma en una lista de PersonajeResponseDto
    private List<PeliculaResponseDto> convertEntityToDto(List<Pelicula> peliculas) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        List<PeliculaResponseDto> peliculaDTO = new ArrayList<>();

        for (Pelicula pelicula : peliculas) {
            peliculaDTO.add(modelMapper.map(pelicula, PeliculaResponseDto.class));
        }

        return peliculaDTO;
    }
}
