package com.challenge.alkemy.service;

import com.challenge.alkemy.entity.Genero;
import com.challenge.alkemy.entity.dto.peliculaDto.PeliculaMapper;
import com.challenge.alkemy.entity.dto.peliculaDto.request.CreatePeliculaRequestDto;
import com.challenge.alkemy.entity.dto.peliculaDto.request.UpdatePeliculaRequestDto;
import com.challenge.alkemy.entity.dto.peliculaDto.response.PeliculaConDetalleResponseDto;
import com.challenge.alkemy.entity.dto.peliculaDto.response.PeliculaBuscadaPorParametroResponseDto;
import com.challenge.alkemy.entity.Pelicula;
import com.challenge.alkemy.entity.Personaje;
import com.challenge.alkemy.error.genero.GeneroNotFoundException;
import com.challenge.alkemy.error.pelicula.PeliculaAlreadyExistsException;
import com.challenge.alkemy.error.pelicula.PeliculaBuscadaPorParametroIncorrectoException;
import com.challenge.alkemy.error.pelicula.PeliculaNotFoundException;
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
    private final GeneroRepository generoRepository;
    private final PeliculaMapper peliculaMapper;

    @Override
    public List<PeliculaConDetalleResponseDto> getAllPeliculas() {

        List<Pelicula> peliculasDB = peliculaRepository.findAll();

        List<PeliculaConDetalleResponseDto> peliculasMapeadas = new ArrayList<>();

        for (Pelicula pelicula: peliculasDB) {
            peliculasMapeadas.add(peliculaMapper.peliculaToDetallePeliculaResponseDto(pelicula));
        }
        return peliculasMapeadas;
    }

    @Override
    public List<PeliculaBuscadaPorParametroResponseDto> getPeliculasByOrder(String orden) throws PeliculaNotFoundException, PeliculaBuscadaPorParametroIncorrectoException {

        if (orden.equals("ASC")) {
            List<Pelicula> peliculasOrdenadas = peliculaRepository.findAllByOrderByTituloAsc();
            if (peliculasOrdenadas.isEmpty()) {
                throw new PeliculaNotFoundException("NO SE ENCONTRARON PELICULAS A ORDENAR");
            }
            return peliculaMapper.peliculaToPeliculaBuscadaPorParametroResponseDto(peliculasOrdenadas);
        }
        if (orden.equals("DESC")) {
            List<Pelicula> peliculasOrdenadas = peliculaRepository.findAllByOrderByTituloDesc();
            if (peliculasOrdenadas.isEmpty()) {
                throw new PeliculaNotFoundException("NO SE ENCONTRARON PELICULAS A ORDENAR");
            }
            return peliculaMapper.peliculaToPeliculaBuscadaPorParametroResponseDto(peliculasOrdenadas);
        }
        throw new PeliculaBuscadaPorParametroIncorrectoException("PARAMETRO DE ORDENAMIENTO INCORRECTO");
    }

    @Override
    public List<PeliculaBuscadaPorParametroResponseDto> getPeliculasByGeneroId(Long generoId) throws GeneroNotFoundException {

        Genero generoDB = generoRepository.findById(generoId).orElseThrow(()-> new GeneroNotFoundException("NO SE ENCONTRO GENERO CON ESE ID"));
        // Del Genero obtenemos sus Peliculas asociadas y las guardamos en una lista.
        List<Pelicula> peliculasObtenidasDelGenero = generoDB.getPeliculas();
        return peliculaMapper.peliculaToPeliculaBuscadaPorParametroResponseDto(peliculasObtenidasDelGenero);
    }

    @Override
    @Transactional
    public void deletePeliculaById(Long peliculaId) throws PeliculaNotFoundException {

        Pelicula peliculaDB = peliculaRepository.findById(peliculaId).orElseThrow(()-> new PeliculaNotFoundException("PELICULA NO ENCONTRADA"));
        peliculaRepository.delete(peliculaDB);
    }

    @Override
    public PeliculaConDetalleResponseDto updatePelicula(@Valid Long peliculaId, UpdatePeliculaRequestDto peliculaRequest) throws PeliculaNotFoundException, PersonajeNotFoundException, PeliculaAlreadyExistsException {

        // Aqui chequeamos que el titulo que se desea ingresar, no este en uso.
        if (peliculaRepository.findByTitulo(peliculaRequest.getTitulo()).isPresent()) {
            throw new PeliculaAlreadyExistsException("EL TITULO SOLICITADO YA EXISTE");
        }
        Pelicula peliculaDB = peliculaRepository.findById(peliculaId).orElseThrow(()-> new PeliculaNotFoundException("NO SE ENCONTRO PELICULA CON ESE ID"));
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
    public PeliculaConDetalleResponseDto addPersonajeToPelicula(Long idMovie, Long idCharacter) throws PeliculaNotFoundException, PersonajeNotFoundException, PersonajeYaEnUsoException {

        Personaje personajeToAdd = personajeRepository.findById(idCharacter).orElseThrow(()-> new PersonajeNotFoundException("NO SE ENCONTRO PERSONAJE CON ESE ID"));
        Pelicula peliculaDB = peliculaRepository.findById(idMovie).orElseThrow(()-> new PeliculaNotFoundException("NO SE ENCONTRO PELICULA CON ESE ID"));

        // Chequemos si la pelicula no tiene ya al personaje que se quiere agregar.
        List<Personaje> personajesInMovie = peliculaDB.getPersonajes();
        for (Personaje personaje:personajesInMovie) {
            if (personaje.getPersonajeId() == idCharacter) {
                throw new PersonajeYaEnUsoException("EL PERSONAJE QUE DESEA AGREGAR YA ESTA EN LA PELICULA");
            }
        }
        personajesInMovie.add(personajeToAdd);
        peliculaDB.setPersonajes(personajesInMovie);
        peliculaDB.setPeliculaId(idMovie);

        return peliculaMapper.peliculaToDetallePeliculaResponseDto(peliculaRepository.save(peliculaDB));
    }

    @Override
    public PeliculaConDetalleResponseDto deletePersonajeDePelicula(Long idMovie, Long idCharacter) throws PersonajeNotFoundException, PeliculaNotFoundException, PersonajeNotFoundInPeliculaException {

        // Chequeamos si el personaje a eliminar existe.
        Personaje personajeToDelete = personajeRepository.findById(idCharacter).orElseThrow(()-> new PersonajeNotFoundException("NO SE ENCONTRO UN PERSONAJE CON ESE ID"));

        // Chequeaamos si la pelicila existe.
        Pelicula peliculaDB = peliculaRepository.findById(idMovie).orElseThrow(()-> new PeliculaNotFoundException("NO SE ENCONTRO PELICULA CON ESE ID"));

        List<Personaje> personajesInMovie = peliculaDB.getPersonajes();

        if (!personajesInMovie.contains(personajeToDelete)) {
            throw new PersonajeNotFoundInPeliculaException("NO SE ENCONTRO EL PERSONAJE A ELIMINAR EN ESTA PELICULA");
        }
        personajesInMovie.remove(personajeToDelete);
        peliculaDB.setPersonajes(personajesInMovie);

        return peliculaMapper.peliculaToDetallePeliculaResponseDto(peliculaRepository.save(peliculaDB));
    }

    @Override
    public PeliculaConDetalleResponseDto createPelicula(@Valid CreatePeliculaRequestDto peliculaRequest) throws PeliculaAlreadyExistsException, PersonajeNotFoundException {

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
    public PeliculaConDetalleResponseDto getPeliculaById(Long peliculaId) throws PeliculaNotFoundException {

        Pelicula peliculaDB = peliculaRepository.findById(peliculaId).orElseThrow(()-> new PeliculaNotFoundException("NO SE ENCONTRO NINGUNA PELICULA CON ESE ID"));
        return peliculaMapper.peliculaToDetallePeliculaResponseDto(peliculaDB);
    }

    @Override
    public List<PeliculaBuscadaPorParametroResponseDto> getPeliculasSinParametros() throws PeliculaNotFoundException {
        List<Pelicula> peliculas = peliculaRepository.findAll();
        if (peliculas.isEmpty()) {
            throw new PeliculaNotFoundException("NO SE ENCONTRARON PELICULAS");
        }
        return peliculaMapper.peliculaToPeliculaBuscadaPorParametroResponseDto(peliculas);
    }

    @Override
    public PeliculaBuscadaPorParametroResponseDto getPeliculaByTitulo(String titulo) throws PeliculaNotFoundException {
        Pelicula pelicula = peliculaRepository.findByTitulo(titulo).orElseThrow(() -> new PeliculaNotFoundException("NO SE ENCONTRO PELICULA CON ESE TITULO"));
        return peliculaMapper.peliculaToPeliculaBuscadaPorTituloDtoResponse(pelicula);
    }

}
