package com.challenge.alkemy.service;

import com.challenge.alkemy.entity.Genero;
import com.challenge.alkemy.entity.dto.generoDto.GeneroMapper;
import com.challenge.alkemy.entity.dto.generoDto.request.CreateGeneroRequestDto;
import com.challenge.alkemy.entity.dto.generoDto.response.CreateGeneroResponseDto;
import com.challenge.alkemy.entity.dto.generoDto.response.GeneroResponseDto;
import com.challenge.alkemy.error.genero.GeneroAlreadyInUseException;
import com.challenge.alkemy.error.genero.GeneroNotFoundException;
import com.challenge.alkemy.repository.GeneroRepository;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class GeneroServiceImp implements GeneroService{

    private GeneroRepository generoRepository;
    private GeneroMapper generoMapper;

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
        generoRepository.findGeneroByNombre(generoRequest.getNombre()).orElseThrow(()-> new GeneroAlreadyInUseException("EL GENERO QUE DESEA CREAR YA EXISTE"));
        Genero generoToSave = Genero.builder()
                .nombre(generoRequest.getNombre())
                .imagen(generoRequest.getImagen())
                .build();

        Genero generoGuardado = generoRepository.save(generoToSave);
        return generoMapper.generoToCreateGeneroResponseDto(generoGuardado);
    }

    @Override
    public GeneroResponseDto findGeneroById(Long generoId) throws GeneroNotFoundException {
        Genero generoDB = generoRepository.findById(generoId).orElseThrow(()-> new GeneroNotFoundException("NO SE ENCONTRO GENERO CON ESE ID"));
        return generoMapper.generoToGeneroResponseDto(generoDB);
    }

    @Override
    public void deleteGeneroById(Long generoId) throws GeneroNotFoundException {
        // Primero chequeamos que el Genero que queremos eliminar existe.
        generoRepository.findById(generoId).orElseThrow(()-> new GeneroNotFoundException("NO SE ENCONTRO GENERO A ELIMINAR CON ESE ID"));
        generoRepository.deleteById(generoId);
    }
}
