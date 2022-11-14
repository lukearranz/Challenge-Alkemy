package com.challenge.alkemy.controller;

import com.challenge.alkemy.entity.dto.generoDto.request.CreateGeneroRequestDto;
import com.challenge.alkemy.entity.dto.generoDto.response.CreateGeneroResponseDto;
import com.challenge.alkemy.entity.dto.generoDto.response.GeneroResponseDto;
import com.challenge.alkemy.error.genero.GeneroAlreadyInUseException;
import com.challenge.alkemy.error.genero.GeneroNotFoundException;
import com.challenge.alkemy.service.GeneroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class GeneroController {

    private final GeneroService generoService;

    @Operation(summary = "Obtener todos los Generos")
    @GetMapping("/genero")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Generos",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GeneroResponseDto.class)))}),
            @ApiResponse(responseCode = "403", description = "User not authenticated",
                    content = @Content)
    })
    public ResponseEntity getAllGeneros() {

        try {
            return ResponseEntity.ok(generoService.getAllGeneros());
        } catch (Exception e) {
            return new ResponseEntity<>("ALGO SALIO MAL", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Obtener un genero por Id")
    @GetMapping("/genero/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Genero by ID",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GeneroResponseDto.class)))}),
            @ApiResponse(responseCode = "403", description = "User not authenticated",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Genero Not Found",
                    content = @Content)
    })
    public ResponseEntity getGeneroById(@PathVariable("id") Long generoId) {

        try {
            return ResponseEntity.ok(generoService.getGeneroById(generoId));
        } catch (GeneroNotFoundException generoNotFoundException) {
            return new ResponseEntity<>("NO SE ENCONTRO GENERO CON ESE ID", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("ALGO SALIO MAL", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Operation(summary = "Eliminar un genero por id")
    @DeleteMapping("/genero/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hero Removed",
                    content = {@Content}),
            @ApiResponse(responseCode = "403", description = "User not authenticated",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Hero Not Found",
                    content = @Content)
    })
    public ResponseEntity deleteGeneroById(@PathVariable("id") Long generoId) {

        try {
            generoService.deleteGeneroById(generoId);
            return new ResponseEntity("GENERO ELIMINADO CON EXITO", HttpStatus.OK);
        } catch (GeneroNotFoundException generoNotFoundException) {
            return new ResponseEntity("NO SE ENCONTRO GENERO A ELIMINAR CON ESE ID", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity("ALGO SALIO MAL", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Crear un nuevo Genero")
    @PostMapping("/genero")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Genero created",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CreateGeneroResponseDto.class)))}),
            @ApiResponse(responseCode = "403", description = "User not authenticated",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Genero already in use",
                    content = @Content)
    })
    public ResponseEntity createGenero(@Valid @RequestBody CreateGeneroRequestDto genero) {

            try {
                return ResponseEntity.ok(generoService.saveGenero(genero));
            } catch (GeneroAlreadyInUseException generoAlreadyInUseException) {
                return new ResponseEntity("EL GENERO INDICADO YA SE ENCUENTRA EN USO", HttpStatus.BAD_REQUEST);
            } catch (GeneroNotFoundException generoNotFoundException) {
                return new ResponseEntity("EL GENERO A CREAR DEBE CONTENER NOMBRE", HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                return new ResponseEntity("ALGO SALIO MAL", HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }
}
