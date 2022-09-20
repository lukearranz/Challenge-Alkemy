package com.challenge.alkemy.controller;

import com.challenge.alkemy.dto.JwtRequestDto;
import com.challenge.alkemy.dto.JwtResponseDto;
import com.challenge.alkemy.entity.Usuario;
import com.challenge.alkemy.security.service.PasswordService;
import com.challenge.alkemy.security.service.UserService;
import com.challenge.alkemy.security.utility.JWTUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping(path = "/auth")
public class AuthController {

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordService passwordService;

    // Logger for debugging the application
    private final Logger LOGGER = LoggerFactory.getLogger(PersonajeController.class);

    @PostMapping("/login")
    public ResponseEntity<Object> authenticate(@RequestBody JwtRequestDto jwtRequestDto) {
        LOGGER.info("INSIDE AUTHCONTROLLER -----> LOGIN_CONTROLLER");
        String hashedPass = userService.loadUserByUsername(jwtRequestDto.getUsername()).getPassword();
        if (!passwordService.verifyPassword(jwtRequestDto.getPassword(), hashedPass)) {
            return new ResponseEntity<>("USUARIO O CONTRASEÑA INCORRECTOS", HttpStatus.BAD_REQUEST);
        }
        final UserDetails userDetails = userService.loadUserByUsername(jwtRequestDto.getUsername());
        final String token = jwtUtility.generateToken(userDetails);
        return new ResponseEntity<>(new JwtResponseDto(token), HttpStatus.ACCEPTED);
    }

    @PostMapping("/register")
    public String register(@RequestBody Usuario usuario) {
        LOGGER.info("INSIDE AUTHCONTROLLER -----> REGISTER_CONTROLLER");
        return userService.createUser(usuario);
    }


}
