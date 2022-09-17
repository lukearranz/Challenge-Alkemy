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
    private ModelMapper modelMapper;

    @Override
    public List<Pelicula> fetchAllPeliculas() {
        return peliculaRepository.findAll();
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

        return peliculaRepository.save(peliculaDB);

    }

    @Override
    public ResponseEntity<Object> savePelicula(Pelicula pelicula) {
        List<Pelicula> peliculaDB = peliculaRepository.findByTitulo(pelicula.getTitulo());
        if (peliculaDB.isEmpty()) {
            return ResponseEntity.ok(peliculaRepository.save(pelicula));
        }
        return new ResponseEntity<>("El Titulo solicitado ya existe", HttpStatus.BAD_REQUEST);
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
