package com.challenge.alkemy.service;

import com.challenge.alkemy.entity.Genero;
import com.challenge.alkemy.entity.dto.peliculaDto.PeliculaMapper;
import com.challenge.alkemy.entity.dto.peliculaDto.request.CreatePeliculaRequestDto;
import com.challenge.alkemy.entity.dto.peliculaDto.response.CreatePeliculaResponseDto;
import com.challenge.alkemy.entity.dto.peliculaDto.response.PeliculaBuscadaPorParametroResponseDto;
import com.challenge.alkemy.entity.Pelicula;
import com.challenge.alkemy.entity.Personaje;
import com.challenge.alkemy.error.genero.GeneroNotFoundException;
import com.challenge.alkemy.error.pelicula.PeliculaAlreadyExistsException;
import com.challenge.alkemy.error.pelicula.PeliculaNotFound;
import com.challenge.alkemy.error.personaje.PersonajeNotFoundException;
import com.challenge.alkemy.repository.GeneroRepository;
import com.challenge.alkemy.repository.PeliculaRepository;
import com.challenge.alkemy.repository.PersonajeRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PeliculaServiceImp implements PeliculaService{
    
    private final PeliculaRepository peliculaRepository;
    private final PersonajeRepository personajeRepository;
    private final PersonajeService personajeService;
    private final GeneroRepository generoRepository;
    private final PeliculaMapper peliculaMapper;

    @Override
    public ResponseEntity<Object> fetchAllPeliculas() {
        List<Pelicula> peliculasDB = peliculaRepository.findAll();
        if (peliculasDB.isEmpty()) {
            return new ResponseEntity<>("NO SE ENCONTRARON PELICULAS", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(peliculasDB, HttpStatus.OK);
    }

    @Override
    public List<PeliculaBuscadaPorParametroResponseDto> fetchPeliculasByOrder(String orden) {
        if (orden.equals("ASC")) {
            return peliculaMapper.peliculaToPeliculaBuscadaPorParametroResponseDto(peliculaRepository.findAllByOrderByTituloAsc());
        }
        if (orden.equals("DESC")) {
            return peliculaMapper.peliculaToPeliculaBuscadaPorParametroResponseDto(peliculaRepository.findAllByOrderByTituloDesc());
        }
        return null;
    }

    @Override
    public List<PeliculaBuscadaPorParametroResponseDto> fetchPeliculasByGeneroId(Long generoId) {

        // Buscamos el genero por ID
        Optional<Genero> generoDB = generoRepository.findById(generoId);
        // Del Genero obtenemos sus Peliculas asociadas y las guardamos en una lista.
        List<Pelicula> peliculasObtenidasDelGenero = generoDB.orElseThrow().getPeliculas();

        return peliculaMapper.peliculaToPeliculaBuscadaPorParametroResponseDto(peliculasObtenidasDelGenero);
    }

    @Override
    @Transactional
    public void deletePeliculaById(Long peliculaId) throws Exception {
        Pelicula peliculaDB = peliculaRepository.findById(peliculaId).orElseThrow(() -> new Exception("Pelicula a eliminar no encontrada"));
        peliculaRepository.delete(peliculaDB);
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
        peliculaDB.setPersonajes(pelicula.getPersonajes());
        peliculaDB.setGenero(pelicula.getGenero());

        return peliculaRepository.save(peliculaDB);

    }

    @Override
    public ResponseEntity<Object> agregarPersonajeToPelicula(Long idMovie, Long idCharacter) {

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
    public ResponseEntity<Object> eliminarPersonajeDePelicula(Long idMovie, Long idCharacter) {

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
    public CreatePeliculaResponseDto createPelicula(@Valid CreatePeliculaRequestDto peliculaRequest) throws PeliculaAlreadyExistsException, PersonajeNotFoundException, GeneroNotFoundException {

        if (peliculaRepository.findByTitulo(peliculaRequest.getTitulo()).isPresent()) {
            throw new PeliculaAlreadyExistsException("LA PELICULA YA EXISTE");
        }
        
        List<Optional<Personaje>> lista = peliculaRequest.getPersonajesId().stream().map(personajeRepository::findById).collect(Collectors.toList());

        for (Optional<Personaje> personaje: lista) {
            if (personaje.isEmpty()) {
                throw new PersonajeNotFoundException("NO SE ENCONTRO EL PERSONAJE");
            }
        }

        Genero genero = generoRepository.findById(peliculaRequest.getGeneroId()).orElseThrow(GeneroNotFoundException::new);

        Pelicula peliculaToSave = Pelicula.builder()
                .fechaEstreno(peliculaRequest.getFechaEstreno())
                .calificacion(peliculaRequest.getCalificacion())
                .imagen(peliculaRequest.getImagen())
                .titulo(peliculaRequest.getTitulo())
                .personajes(lista.stream().map(personajeOptional -> personajeOptional.get()).collect(Collectors.toList()))
                .genero(genero)
                .build();
        Pelicula peliculaGuardada = peliculaRepository.save(peliculaToSave);

        return peliculaMapper.peliculaToCreatePeliculaResponseDto(peliculaGuardada);
    }

    @Override
    public Optional<Pelicula> fetchPeliculaById(Long peliculaId) {
        return peliculaRepository.findById(peliculaId);
    }

    @Override
    public List<PeliculaBuscadaPorParametroResponseDto> fetchPeliculasSinParametros() {
        return peliculaMapper.peliculaToPeliculaBuscadaPorParametroResponseDto(peliculaRepository.findAll());
    }

    @Override
    public PeliculaBuscadaPorParametroResponseDto fetchPeliculaByTitulo(String titulo) throws PeliculaNotFound {
        Pelicula pelicula = peliculaRepository.findByTitulo(titulo).orElseThrow(() -> new PeliculaNotFound("No se encontro la pelicula"));
        return peliculaMapper.peliculaToPeliculaBuscadaPorTituloDtoResponse(pelicula);
    }

}
