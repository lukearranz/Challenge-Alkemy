package com.challenge.alkemy.service;

import com.challenge.alkemy.dto.request.CreatePeliculaRequestDto;
import com.challenge.alkemy.dto.PeliculaResponseDto;
import com.challenge.alkemy.dto.response.CreatePeliculaResponseDto;
import com.challenge.alkemy.entity.Genero;
import com.challenge.alkemy.entity.Pelicula;
import com.challenge.alkemy.entity.Personaje;
import com.challenge.alkemy.error.ChallengeAlkemyException;
import com.challenge.alkemy.repository.GeneroRepository;
import com.challenge.alkemy.repository.PeliculaRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
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

    @Autowired
    private GeneroRepository generoRepository;

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
    public ResponseEntity<Object> createPelicula( @Valid CreatePeliculaRequestDto peliculaRequest) throws ChallengeAlkemyException {
        // Aca fijate que en CreatePeliculaRequestDto ya le puse la limitacion y el mensaje de error para no tener que chequear en el service de todos modos no esta mal hacer 2 checks.
       // LINK ->  https://www.baeldung.com/spring-boot-bean-validation
        if (peliculaRequest.getCalificacion() < 1 || peliculaRequest.getCalificacion() > 5) {
            return ResponseEntity.badRequest().body( "LA CALIFICACION DEBE ESTAR ENTRE 1 Y 5");
        }
        // aca podes dejarlo mejor
        // asi estaba List<Pelicula> peliculaDB = peliculaRepository.findByTitulo(pelicula.getTitulo());
        if (peliculaRepository.findByTitulo(peliculaRequest.getTitulo()).isEmpty() ) {
            return ResponseEntity.badRequest().body(String.format( "La pelicula con titulo %s ya existe.", peliculaRequest.getTitulo()));
        }
        // Los errores siempre es mejor devolverlos cuando suceden no esperar al final
        // linea uno
        // linea 2 donde esta el error
        // linea 3
        // return devuelve error - es preferible cortar la ejecucion en este caso en linea 2. porque debuggeando es mas facil de ver donde esta el error.
        //return new ResponseEntity<>("EL TITULO SOLICITADO YA EXISTE", HttpStatus.BAD_REQUEST);

        // hay que ver como se maneja esa exception aca podes ver varias formas https://www.baeldung.com/exception-handling-for-rest-with-spring a mi me gusta @ControllerAdvice
        Genero genero = generoRepository.findById(peliculaRequest.getGeneroId()).orElseThrow( () -> new ChallengeAlkemyException("Genero no encontrado") );

        Pelicula pelicula = Pelicula.builder()
                .calificacion(peliculaRequest.getCalificacion())
                .fechaEstreno(peliculaRequest.getFechaEstreno())
                .genero(genero)
                .imagen(peliculaRequest.getImagen())
                .titulo(peliculaRequest.getTitulo())
                .build();

        Pelicula peliculaGuardada = peliculaRepository.save(pelicula);

        return ResponseEntity.ok(peliculaToCreatePeliculaDtoResponseMapper(peliculaGuardada));
    }
    // Esto lo podes sacar a una clase aparte que se llame mapper o util
    // uno muy uesado es https://www.baeldung.com/mapstruct

    // una cosa que es impoprtante fijate que en genero develve un string aca podrias devolver el id o el genero entero si es necesario en ese caso tendrias que devolver GeneroDto y que ese dto tenga el id y el nombre o lo que sea
    private CreatePeliculaResponseDto peliculaToCreatePeliculaDtoResponseMapper(Pelicula pelicula){
        return CreatePeliculaResponseDto.builder()
                .calificacion(pelicula.getCalificacion())
                .fechaEstreno(pelicula.getFechaEstreno())
                .genero(pelicula.getGenero().getNombre())
                .id(pelicula.getPeliculaId())
                .titulo(pelicula.getTitulo())
                .build();
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
