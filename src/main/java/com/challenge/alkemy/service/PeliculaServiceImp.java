package com.challenge.alkemy.service;

import com.challenge.alkemy.entity.Genero;
import com.challenge.alkemy.entity.dto.peliculaDto.PeliculaMapper;
import com.challenge.alkemy.entity.dto.peliculaDto.request.CreatePeliculaRequestDto;
import com.challenge.alkemy.entity.dto.peliculaDto.request.UpdatePeliculaRequestDto;
import com.challenge.alkemy.entity.dto.peliculaDto.response.DetallePeliculaResponseDto;
import com.challenge.alkemy.entity.dto.peliculaDto.response.PeliculaBuscadaPorParametroResponseDto;
import com.challenge.alkemy.entity.Pelicula;
import com.challenge.alkemy.entity.Personaje;
import com.challenge.alkemy.error.genero.GeneroNotFoundException;
import com.challenge.alkemy.error.pelicula.PeliculaAlreadyExistsException;
import com.challenge.alkemy.error.pelicula.PeliculaBuscadaPorParametroIncorrectoException;
import com.challenge.alkemy.error.pelicula.PeliculaNotFound;
import com.challenge.alkemy.error.personaje.PersonajeNotFoundException;
import com.challenge.alkemy.error.personaje.PersonajeNotFoundInPeliculaException;
import com.challenge.alkemy.error.personaje.PersonajeYaEnUsoException;
import com.challenge.alkemy.repository.GeneroRepository;
import com.challenge.alkemy.repository.PeliculaRepository;
import com.challenge.alkemy.repository.PersonajeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
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
    public List<DetallePeliculaResponseDto> fetchAllPeliculas() throws PeliculaNotFound {
        List<Pelicula> peliculasDB = peliculaRepository.findAll();
        if (peliculasDB.isEmpty()) {
            throw new PeliculaNotFound("NO SE ENCONTRARON PELICULAS");
        }
        List<DetallePeliculaResponseDto> peliculasMapeadas = new ArrayList<>();
        for (Pelicula pelicula: peliculasDB) {
            peliculasMapeadas.add(peliculaMapper.peliculaToDetallePeliculaResponseDto(pelicula));
        }
        return peliculasMapeadas;
    }

    @Override
    public List<PeliculaBuscadaPorParametroResponseDto> fetchPeliculasByOrder(String orden) throws PeliculaNotFound, PeliculaBuscadaPorParametroIncorrectoException {
        if (orden.equals("ASC")) {
            List<Pelicula> peliculasOrdenadas = peliculaRepository.findAllByOrderByTituloAsc();
            if (peliculasOrdenadas.isEmpty()) {
                throw new PeliculaNotFound("NO SE ENCONTRARON PELICULAS A ORDENAR");
            }
            return peliculaMapper.peliculaToPeliculaBuscadaPorParametroResponseDto(peliculasOrdenadas);
        }
        if (orden.equals("DESC")) {
            List<Pelicula> peliculasOrdenadas = peliculaRepository.findAllByOrderByTituloDesc();
            if (peliculasOrdenadas.isEmpty()) {
                throw new PeliculaNotFound("NO SE ENCONTRARON PELICULAS A ORDENAR");
            }
            return peliculaMapper.peliculaToPeliculaBuscadaPorParametroResponseDto(peliculasOrdenadas);
        }
        throw new PeliculaBuscadaPorParametroIncorrectoException("PARAMETRO DE ORDENAMIENTO INCORRECTO");
    }

    @Override
    public List<PeliculaBuscadaPorParametroResponseDto> fetchPeliculasByGeneroId(Long generoId) throws GeneroNotFoundException {
        Optional<Genero> generoDB = generoRepository.findById(generoId);
        if (generoDB.isEmpty()) {
            throw new GeneroNotFoundException("NO SE ENCONTRO GENERO CON ESE ID");
        }
        // Del Genero obtenemos sus Peliculas asociadas y las guardamos en una lista.
        List<Pelicula> peliculasObtenidasDelGenero = generoDB.orElseThrow().getPeliculas();
        return peliculaMapper.peliculaToPeliculaBuscadaPorParametroResponseDto(peliculasObtenidasDelGenero);
    }

    @Override
    @Transactional
    public void deletePeliculaById(Long peliculaId) throws PeliculaNotFound {
        Optional<Pelicula> peliculaDB = peliculaRepository.findById(peliculaId);
        if (peliculaDB.isEmpty()) {
            throw new PeliculaNotFound("PELICULA NO ENCONTRADA");
        }
        peliculaRepository.delete(peliculaDB.get());
    }

    @Override
    public DetallePeliculaResponseDto updatePelicula(@Valid Long peliculaId, UpdatePeliculaRequestDto peliculaRequest) throws PeliculaNotFound, PersonajeNotFoundException, PeliculaAlreadyExistsException {

        // Aqui chequeamos que el titulo que se desea ingresar, no este en uso.
        if (peliculaRepository.findFirstByTitulo(peliculaRequest.getTitulo()).isPresent()) {
            throw new PeliculaAlreadyExistsException("EL TITULO SOLICITADO YA EXISTE");
        }

        Optional<Pelicula> peliculaDB = peliculaRepository.findById(peliculaId);
        if (peliculaDB.isEmpty()) {
            throw new PeliculaNotFound("NO SE ENCONTRO PELICULA CON ESE ID");
        }

        List<Optional<Personaje>> lista = peliculaRequest.getPersonajesId().stream().map(personajeRepository::findById).collect(Collectors.toList());
        for (Optional<Personaje> personaje: lista) {
            if (personaje.isEmpty()) {
                throw new PersonajeNotFoundException("NO SE ENCONTRO EL PERSONAJE");
            }
        }

        Genero genero = generoRepository.findById(peliculaRequest.getGeneroId()).orElseThrow();

        Pelicula peliculaToUpdate = Pelicula.builder()
                .peliculaId(peliculaId)
                .fechaEstreno(peliculaRequest.getFechaEstreno())
                .calificacion(peliculaRequest.getCalificacion())
                .imagen(peliculaRequest.getImagen())
                .titulo(peliculaRequest.getTitulo())
                .personajes(lista.stream().map(personajeOptional -> personajeOptional.get()).collect(Collectors.toList()))
                .genero(genero)
                .build();

        Pelicula peliculaEditada = peliculaRepository.save(peliculaToUpdate);
        return peliculaMapper.peliculaToDetallePeliculaResponseDto(peliculaEditada);
    }

    @Override
    public DetallePeliculaResponseDto agregarPersonajeToPelicula(Long idMovie, Long idCharacter) throws PeliculaNotFound, PersonajeNotFoundException, PersonajeYaEnUsoException {
        // Chequeamos si el personaje a agregar existe.
        Optional<Personaje> personajeToAdd = personajeService.fetchPersonajeById(idCharacter);
        if (personajeToAdd.isEmpty()) {
            throw new PersonajeNotFoundException("NO SE ENCONTRO UN PERSONJE CON ESE ID");
        }
        // Chequeaamos si la pelicila existe.
        Optional<Pelicula> peliculaDB = peliculaRepository.findById(idMovie);
        if (peliculaDB.isEmpty()) {
            throw new PeliculaNotFound("NO SE ENCONTRO PELICULA CON ESE ID");
        }
        // Chequemos si la pelicula no tiene ya al personaje que se quiere agregar.
        List<Personaje> personajesInMovie = peliculaDB.get().getPersonajes();
        for (Personaje personaje:personajesInMovie) {
            if (personaje.getPersonajeId() == idCharacter) {
                throw new PersonajeYaEnUsoException("EL PERSONAJE QUE DESEA AGREGAR YA ESTA EN LA PELICULA");
            }
        }

        Personaje personajeToSave = personajeToAdd.get();
        Pelicula peliculaToSave = peliculaDB.get();

        personajesInMovie.add(personajeToSave);
        peliculaToSave.setPersonajes(personajesInMovie);
        peliculaToSave.setPeliculaId(idMovie);

        return peliculaMapper.peliculaToDetallePeliculaResponseDto(peliculaRepository.save(peliculaToSave));
    }

    @Override
    public DetallePeliculaResponseDto eliminarPersonajeDePelicula(Long idMovie, Long idCharacter) throws PersonajeNotFoundException, PeliculaNotFound, PersonajeNotFoundInPeliculaException {

        // Chequeamos si el personaje a eliminar existe.
        Optional<Personaje> personajeToDelete = personajeService.fetchPersonajeById(idCharacter);
        if (personajeToDelete.isEmpty()) {
            throw new PersonajeNotFoundException("NO SE ENCONTRO UN PERSONJE CON ESE ID");
        }
        // Chequeaamos si la pelicila existe.
        Optional<Pelicula> peliculaDB = peliculaRepository.findById(idMovie);
        if (peliculaDB.isEmpty()) {
            throw new PeliculaNotFound("NO SE ENCONTRO PELICULA CON ESE ID");
        }

        Pelicula peliculaToEdit = peliculaDB.orElseThrow();
        List<Personaje> personajesInMovie = peliculaToEdit.getPersonajes();

        if (!personajesInMovie.contains(personajeToDelete.get())) {
            throw new PersonajeNotFoundInPeliculaException("NO SE ENCONTRO EL PERSONAJE A ELIMINAR EN ESTA PELICULA");
        }
        personajesInMovie.remove(personajeToDelete.get());
        peliculaToEdit.setPersonajes(personajesInMovie);

        return peliculaMapper.peliculaToDetallePeliculaResponseDto(peliculaRepository.save(peliculaToEdit));
    }

    @Override
    public DetallePeliculaResponseDto createPelicula(@Valid CreatePeliculaRequestDto peliculaRequest) throws PeliculaAlreadyExistsException, PersonajeNotFoundException {

        if (peliculaRepository.findByTitulo(peliculaRequest.getTitulo()).isPresent()) {
            throw new PeliculaAlreadyExistsException("LA PELICULA YA EXISTE");
        }
        List<Optional<Personaje>> lista = peliculaRequest.getPersonajesId().stream().map(personajeRepository::findById).collect(Collectors.toList());

        for (Optional<Personaje> personaje: lista) {
            if (personaje.isEmpty()) {
                throw new PersonajeNotFoundException("NO SE ENCONTRO EL PERSONAJE");
            }
        }
        Genero genero = generoRepository.findById(peliculaRequest.getGeneroId()).orElseThrow();

        Pelicula peliculaToSave = Pelicula.builder()
                .fechaEstreno(peliculaRequest.getFechaEstreno())
                .calificacion(peliculaRequest.getCalificacion())
                .imagen(peliculaRequest.getImagen())
                .titulo(peliculaRequest.getTitulo())
                .personajes(lista.stream().map(personajeOptional -> personajeOptional.get()).collect(Collectors.toList()))
                .genero(genero)
                .build();
        Pelicula peliculaGuardada = peliculaRepository.save(peliculaToSave);

        return peliculaMapper.peliculaToDetallePeliculaResponseDto(peliculaGuardada);
    }

    @Override
    public DetallePeliculaResponseDto fetchPeliculaById(Long peliculaId) throws PeliculaNotFound {
        Optional<Pelicula> peliculaDB = peliculaRepository.findById(peliculaId);
        if (peliculaDB.isEmpty()) {
            throw new PeliculaNotFound("NO SE ENCONTRO NINGUNA PELICULA CON ESE ID");
        }
        return peliculaMapper.peliculaToDetallePeliculaResponseDto(peliculaDB.get());
    }

    @Override
    public List<PeliculaBuscadaPorParametroResponseDto> fetchPeliculasSinParametros() throws PeliculaNotFound {
        List<Pelicula> peliculas = peliculaRepository.findAll();
        if (peliculas.isEmpty()) {
            throw new PeliculaNotFound("NO SE ENCONTRARON PELICULAS");
        }
        return peliculaMapper.peliculaToPeliculaBuscadaPorParametroResponseDto(peliculas);
    }

    @Override
    public PeliculaBuscadaPorParametroResponseDto fetchPeliculaByTitulo(String titulo) throws PeliculaNotFound {
        Pelicula pelicula = peliculaRepository.findByTitulo(titulo).orElseThrow(() -> new PeliculaNotFound("NO SE ENCONTRO PELICULA CON ESE TITULO"));
        return peliculaMapper.peliculaToPeliculaBuscadaPorTituloDtoResponse(pelicula);
    }

}
