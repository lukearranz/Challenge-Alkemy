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
import java.util.Optional;
import java.util.function.Consumer;


@Service
@AllArgsConstructor
public class GeneroServiceImp implements GeneroService {

    private final GeneroRepository generoRepository;
    private final GeneroMapper generoMapper;

    @Override
    public List<GeneroResponseDto> getAllGeneros() {

        List<Genero> generosDB = generoRepository.findAll();
        return generoMapper.generosToGenerosResponseDto(generosDB);
    }

    @Override
    public CreateGeneroResponseDto saveGenero(CreateGeneroRequestDto generoRequest) throws GeneroAlreadyInUseException, GeneroNotFoundException {

        if (generoRequest.getNombre().isEmpty()) {
            throw new GeneroNotFoundException("EL GENERO DEBE CONTENER NOMBRE");
        }
        Optional<Genero> generoDB = generoRepository.findGeneroByNombre(generoRequest.getNombre());

        if (generoDB.isPresent()) {
            throw new GeneroAlreadyInUseException("EL GENERO QUE DESEA CREAR YA EXISTE");
        }

        Genero generoToSave = Genero.builder()
                .nombre(generoRequest.getNombre())
                .imagen(generoRequest.getImagen())
                .build();

        return generoMapper.generoToCreateGeneroResponseDto(generoRepository.save(generoToSave));
    }

    @Override
    public GeneroResponseDto getGeneroById(Long generoId) throws GeneroNotFoundException {

        Genero generoDB = generoRepository.findById(generoId).orElseThrow(()-> new GeneroNotFoundException("NO SE ENCONTRO GENERO CON ESE ID"));
        return generoMapper.generoToGeneroResponseDto(generoDB);
    }

    @Override
    public void deleteGeneroById(Long generoId) throws GeneroNotFoundException {

        generoRepository.findById(generoId).orElseThrow(()-> new GeneroNotFoundException("NO SE ENCONTRO GENERO A ELIMINAR CON ESE ID"));
        generoRepository.deleteById(generoId);
    }
}
