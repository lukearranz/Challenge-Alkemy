package com.challenge.alkemy.controller;

import com.challenge.alkemy.dto.JwtRequestDto;
import com.challenge.alkemy.dto.JwtResponseDto;
import com.challenge.alkemy.security.service.UserService;
import com.challenge.alkemy.security.utility.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
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

    @PostMapping("/login")
    public JwtResponseDto authenticate(@RequestBody JwtRequestDto jwtRequestDto) throws  Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequestDto.getUsername(),
                            jwtRequestDto.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw  new Exception("Invalid Credentials", e);
        }

        final UserDetails userDetails = userService.loadUserByUsername(jwtRequestDto.getUsername());
        final String token = jwtUtility.generateToken(userDetails);

        return new JwtResponseDto(token);
    }


}
