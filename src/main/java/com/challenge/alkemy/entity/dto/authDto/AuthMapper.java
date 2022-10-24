package com.challenge.alkemy.entity.dto.authDto;

import com.challenge.alkemy.entity.Usuario;
import com.challenge.alkemy.entity.dto.authDto.request.RegisterRequestDto;
import com.challenge.alkemy.entity.dto.authDto.response.RegisterResponseDto;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    public Usuario registerRequestDtoToUsuario(RegisterRequestDto userRequest) {
        return Usuario.builder()
                .username(userRequest.getUsername())
                .password(userRequest.getPassword())
                .email(userRequest.getEmail())
                .build();
    }

    public RegisterResponseDto usuarioToRegisterResponseDto(Usuario usuario) {
        return RegisterResponseDto.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .email(usuario.getEmail())
                .build();
    }

}
