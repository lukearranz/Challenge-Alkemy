package com.challenge.alkemy.controller;

import com.challenge.alkemy.entity.dto.jwtDto.JwtRequestDto;
import com.challenge.alkemy.entity.dto.jwtDto.JwtResponseDto;
import com.challenge.alkemy.entity.Usuario;
import com.challenge.alkemy.error.user.UserAndPassDontMatchException;
import com.challenge.alkemy.error.user.UsernameAlreadyTakenException;
import com.challenge.alkemy.security.service.PasswordService;
import com.challenge.alkemy.security.service.UserService;
import com.challenge.alkemy.security.utility.JWTUtility;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth")
@AllArgsConstructor
public class AuthController {

    private JWTUtility jwtUtility;
    private UserService userService;
    private PasswordService passwordService;
    // make fields final

    // Use LOG4J2
    // Logger for debugging the application
    private final Logger LOGGER = LoggerFactory.getLogger(PersonajeController.class);

    // Add @valid in the method to validate that the endpoint validates the contrains
    @PostMapping("/login")
    public ResponseEntity authenticate(@RequestBody JwtRequestDto jwtRequestDto) {

        LOGGER.info("INSIDE AUTHCONTROLLER -----> LOGIN_CONTROLLER");
        try {
            String hashedPass = userService.loadUserByUsername(jwtRequestDto.getUsername()).getPassword();
            // Chequeamos que usuario y contraseña hagan match.
            // remove comment the name of the method verifyPassword its clear enought.
            passwordService.verifyPassword(jwtRequestDto.getPassword(), hashedPass);
            final UserDetails userDetails = userService.loadUserByUsername(jwtRequestDto.getUsername());
            final String token = jwtUtility.generateToken(userDetails);
            return new ResponseEntity<>(new JwtResponseDto(token), HttpStatus.ACCEPTED);
        } catch (UserAndPassDontMatchException usernameNotFoundException) {
            return new ResponseEntity("USUARIO O CONTRASEÑA INCORRECTOS", HttpStatus.BAD_REQUEST);
        } catch (UsernameNotFoundException usernameNotFoundException) {
            return new ResponseEntity("EL USUARIO NO EXISTE", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody Usuario usuario) {

        LOGGER.info("INSIDE AUTHCONTROLLER -----> REGISTER_CONTROLLER");
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
