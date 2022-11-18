package com.challenge.alkemy.controller;

import com.challenge.alkemy.entity.dto.authDto.request.AuthRequestDto;
import com.challenge.alkemy.entity.dto.authDto.request.RegisterRequestDto;
import com.challenge.alkemy.entity.dto.authDto.response.AuthResponseDto;
import com.challenge.alkemy.error.user.UsernameAlreadyTakenException;
import com.challenge.alkemy.security.service.PasswordService;
import com.challenge.alkemy.security.service.UserService;
import com.challenge.alkemy.security.utility.JWTUtility;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/auth")
@AllArgsConstructor
public class AuthController {

    private final JWTUtility jwtUtility;
    private final UserService userService;
    private final PasswordService passwordService;

    @Operation(summary = "Login")
    @PostMapping("/login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User accepted",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = AuthResponseDto.class)))}),
            @ApiResponse(responseCode = "401", description = "User unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)
    })
    public ResponseEntity authenticate(@Valid @RequestBody AuthRequestDto authRequestDto) {

        UserDetails userDetails;
        try {
            userDetails = userService.loadUserByUsername(authRequestDto.getUsername());
        } catch (UsernameNotFoundException usernameNotFoundException) {
            return new ResponseEntity("EL USUARIO NO EXISTE", HttpStatus.NOT_FOUND);
        }

        if (!passwordService.verifyPassword(authRequestDto.getPassword(), userDetails.getPassword())) {
            return new ResponseEntity("USUARIO O CONTRASEÑA INCORRECTOS", HttpStatus.UNAUTHORIZED);
        }
        String token = jwtUtility.generateToken(userDetails);

        return new ResponseEntity(AuthResponseDto.builder().jwtToken(token).build(), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Register")
    @PostMapping("/register")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Succesfull registration",
                    content = {@Content}),
            @ApiResponse(responseCode = "400", description = "User already in use",
                    content = @Content)
    })
    public ResponseEntity register(@Valid @RequestBody RegisterRequestDto usuario) {

        try {
            userService.createUser(usuario);
            return ResponseEntity.ok("USUARIO CREADO CON EXITO");
        } catch (UsernameAlreadyTakenException usernameAlreadyTakenException) {
            return new ResponseEntity("EL USUARIO SOLICITADO YA SE ENCUENTRA EN USO", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity("ALGO SALIO MAL", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
