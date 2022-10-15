package com.challenge.alkemy.service;

import com.challenge.alkemy.entity.Genero;
import com.challenge.alkemy.entity.dto.generoDto.GeneroMapper;
import com.challenge.alkemy.entity.dto.generoDto.request.CreateGeneroRequestDto;
import com.challenge.alkemy.entity.dto.generoDto.response.CreateGeneroResponseDto;
import com.challenge.alkemy.entity.dto.generoDto.response.GeneroResponseDto;
import com.challenge.alkemy.error.genero.GeneroAlreadyInUseException;
import com.challenge.alkemy.error.genero.GeneroNotFoundException;
import com.challenge.alkemy.repository.GeneroRepository;
import com.challenge.alkemy.repository.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GeneroServiceImp implements GeneroService{

    @Autowired
    private GeneroRepository generoRepository;

    @Autowired
    private GeneroMapper generoMapper;

    @Autowired
    private PeliculaRepository peliculaRepository;

    @Override
    public List<GeneroResponseDto> fetchGeneros() throws GeneroNotFoundException {
        List<Genero> generosDB = generoRepository.findAll();
        if (generosDB.isEmpty()) {
            throw new GeneroNotFoundException("NO SE ENCONTRARON GENEROS");
        }
        return generoMapper.generosToGenerosResponseDto(generosDB);
    }

    @Override
    public CreateGeneroResponseDto saveGenero(CreateGeneroRequestDto generoRequest) throws GeneroAlreadyInUseException {
        Optional<Genero> generoDB = generoRepository.findGeneroByNombre(generoRequest.getNombre());
        if (generoDB.isPresent()) {
            throw new GeneroAlreadyInUseException("EL GENERO QUE DESEA CREAR YA EXISTE");
        }

        Genero generoToSave = Genero.builder()
                .nombre(generoRequest.getNombre())
                .imagen(generoRequest.getImagen())
                .build();

        Genero generoGuardado = generoRepository.save(generoToSave);
        return generoMapper.generoToCreateGeneroResponseDto(generoGuardado);
    }

    @Override
    public GeneroResponseDto findGeneroById(Long generoId) throws GeneroNotFoundException {
        Optional<Genero> generoDB = generoRepository.findById(generoId);
        if (generoDB.isEmpty()) {
            throw new GeneroNotFoundException("NO SE ENCONTRO GENERO CON ESE ID");
        }
        return generoMapper.generoToGeneroResponseDto(generoDB.get());
    }

    @Override
    public void deleteGeneroById(Long generoId) throws GeneroNotFoundException {
        Optional<Genero> generoBD = generoRepository.findById(generoId);
        if (generoBD.isEmpty()) {
            throw new GeneroNotFoundException("NO SE ENCONTRO GENERO A ELIMINAR CON ESE ID");
        }
        generoRepository.deleteById(generoId);
    }
}
